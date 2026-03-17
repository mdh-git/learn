#  Sentinel
~~~
Sentinel 的熔断机制更加轻量、灵活且精准。它的核心特点可以概括为：基于信号量隔离 + 多维度的统计指标 + 三种熔断策略。
~~~

## 核心原理
~~~
1. 隔离机制：信号量隔离 (Semaphore Isolation)
    Hystrix：默认使用线程池隔离。每个依赖服务分配一个独立的线程池。优点是隔离彻底，缺点是线程上下文切换开销大，吞吐量受限。
    Sentinel：默认使用信号量隔离（并发线程数控制）。它不创建新线程，而是在当前线程中通过计数器判断并发数是否超过阈值。
        优势：极度轻量，无线程切换开销，适合高并发场景。
        适用：绝大多数网络调用（RPC/HTTP）。只有极少数需要异步隔离的场景才考虑线程池。
        
2.统计结构：滑动时间窗口 (Sliding Window)
     Sentinel 使用高效的滑动窗口算法来统计数据（如 QPS、响应时间、异常数）。
        它将时间划分为多个小的“桶”（Bucket，例如每 500ms 一个桶）。
        实时滚动更新这些桶的数据。
        相比 Hystrix，Sentinel 的统计更精细，能更敏锐地感知流量的瞬间变化。
~~~

## 代码
~~~
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // name: 资源名称
    // blockHandler: 处理限流、熔断、系统保护等 BlockException 异常的逻辑
    // fallback: 处理业务异常 (如 timeout, exception) 的降级逻辑
    @SentinelResource(value = "getUserInfo", 
                      blockHandler = "handleBlock", 
                      fallback = "handleFallback")
    public User getUserInfo(Long id) {
        // 模拟调用远程服务
        if (id == 0) {
            throw new RuntimeException("用户不存在"); // 业务异常 -> 触发 fallback
        }
        // 模拟超时或大量异常会触发熔断 -> 后续请求触发 blockHandler (如果是熔断引起的) 
        // 注意：Sentinel 中 blockHandler 主要处理限流和熔断阻断，fallback 处理业务异常和熔断后的兜底
        return remoteService.getUser(id);
    }

    // 熔断/限流 时的处理逻辑 (必须 static 或同类实例，参数最后多一个 BlockException)
    public User handleBlock(Long id, BlockException ex) {
        System.out.println("触发了熔断或限流：" + ex.getRule());
        return new User(0L, "默认用户-熔断中");
    }

    // 业务异常 或 熔断后的兜底逻辑 (可选，优先级低于 blockHandler 针对熔断场景)
    // 在较新版本中，熔断后通常优先走 blockHandler，如果没有配置 blockHandler 则走 fallback
    public User handleFallback(Long id, Throwable t) {
        System.out.println("服务出错了，进行降级：" + t.getMessage());
        return new User(0L, "默认用户-服务错误");
    }
}


关键点：
blockHandler: 专门处理 Sentinel 系统抛出的 BlockException（包括限流、熔断、系统负载过高）。熔断发生时，通常优先调用此方法。
fallback: 处理业务代码抛出的普通异常（如 RuntimeException），也可以作为熔断后的终极兜底（如果没配 blockHandler）。
~~~

~~~
Sentinel 实现熔断，其核心在于：
    1.轻量级：默认信号量隔离，无线程开销。
    2.多维度：支持按异常比例、异常数量、响应时间三种维度自动熔断。
    3.动态化：结合 Nacos 和控制台，实现规则的实时推送和可视化监控。
    4.一体化：将限流和熔断统一在一个框架内解决，避免了引入多个组件的复杂性。
~~~