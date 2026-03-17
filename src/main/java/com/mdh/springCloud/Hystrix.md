# Hystrix
~~~
Spring Cloud Netflix 套件中的核心组件，主要用于实现服务熔断、服务降级、线程隔离和流量监控。
~~~


~~~
服务雪崩:一个服务失败，导致整条链路的服务都失败的情形 
    1.服务降级:服务自我保护的一种方式，或者保护下游服务的一种方式，用于确保服务不会受请求突增影响变得不可用，确保服务不会崩溃，一般在实际开发中与feign接口整合，编写降级逻辑
    2.服务熔断:默认关闭，需要手动打开，如果检测到10秒内请求的失败率超过50%，就触发熔断机制。之后每隔5秒重新尝试请求微服务，如果微服务不能响应，继续走熔断机制。如果微服务可达，则关闭熔断机制，恢复正常请求
~~~

## 服务熔断 (Circuit Breaker)
~~~
原理：模仿电路保险丝。当10s内请求的错误率（或超时率）达到阈值（默认 50%），熔断器打开。
状态机：
    Closed (关闭)：正常调用，统计错误率。
    Open (打开)：直接拒绝所有请求，快速失败，不再调用下游，保护系统。
    Half-Open (半开)：经过一段时间（默认 5 秒）后，允许少量请求通过测试。如果成功，则关闭熔断器；如果失败，继续打开。
~~~

~~~
@FeignClient(name = "user-service", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/user/{id}")
    String getUser(@PathVariable("id") Long id);
}

// 降级实现类，必须实现接口
@Component
public class UserClientFallback implements UserClient {
    @Override
    public String getUser(Long id) {
        return "用户服务繁忙，返回默认数据：User-" + id;
    }
}


@HystrixCommand(fallbackMethod = "getUserFallback")
public String getUser(Long id) {
    return userClient.getUser(id);
}

public String getUserFallback(Long id) {
    return "降级数据";
}
~~~