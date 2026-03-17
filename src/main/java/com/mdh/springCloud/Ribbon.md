
# Ribbon的负载均衡策略
~~~
Spring Cloud 体系中，Ribbon 默认集成在 RestTemplate (配合 @LoadBalanced) 和 Feign 中。

~~~
| 序号 | 策略类名 | 中文名称 | 核心逻辑 | 适用场景 |
| :--- | :--- | :--- | :--- | :--- |
| 1 | `RoundRobinRule` | 轮询策略 (默认) | 按顺序依次选择服务实例。<br>例：有 A, B, C 三个实例，请求顺序为 A→B→C→A... | 最通用。实例性能相近、无状态服务的首选。 |
| 2 | `RandomRule` | 随机策略 | 从可用实例列表中完全随机选择一个。 | 实例数量较多，希望流量分布更离散，避免周期性规律。 |
| 3 | `RetryRule` | 重试策略 | 先按轮询策略选择实例，如果调用失败（超时或异常），则在指定时间内重试选择其他实例，直到成功或超时。 | 对可用性要求高的场景，允许短暂的重试延迟来换取成功。 |
| 4 | `WeightedResponseTimeRule` | 权重响应时间策略 | 根据实例的平均响应时间分配权重。响应越快，权重越大，被选概率越高。<br>*注：刚启动时统计信息不足，会退化为轮询。* | 实例性能差异大（如有的机器配置高，有的低），希望快者多劳。 |
| 5 | `BestAvailableRule` | 最小连接数策略 | 遍历所有实例，选择一个并发请求数最少且未被熔断的实例。 | 需要避免单点过载，保护处理能力较弱的实例。 |
| 6 | `AvailabilityFilteringRule` | 可用过滤策略 | 先过滤掉两类实例：<br>1. 连续多次故障（断路器打开）的实例。<br>2. 并发连接数超过阈值的实例。<br>剩余实例再按轮询选择。 | 网络环境不稳定，或希望快速剔除“病号”实例。 |
| 7 | `ZoneAvoidanceRule` | 区域感知策略 (默认推荐) | Spring Cloud 默认实际使用的策略（即使配置看似是轮询）。<br>结合了区域感知和可用性过滤：<br>1. 优先选择与客户端在同一机房/区域（Zone）的实例。<br>2. 过滤掉故障和高负载实例。<br>3. 剩余实例轮询。 | 多机房/多可用区部署。希望流量优先在局域网内交互，降低延迟和跨区带宽成本。 |


~~~ Ribbon指定策略
# 假设要调用的服务名为 user-service
user-service:
  ribbon:
    # 指定策略类的全限定名
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
    
    # 其他可选参数
    # ConnectTimeout: 5000
    # ReadTimeout: 5000
    # OkToRetryOnAllOperations: false
    
    
通过 @RibbonClient 注解自定义配置类。
@Configuration
@RibbonClient(name = "order-service", configuration = OrderRibbonConfig.class)
public class MyRibbonConfig {
    // 这里只是声明，具体规则在下面的配置类中定义
}

@Configuration
public class OrderRibbonConfig {
    
    @Bean
    public IRule loadBalanceRule() {
        // 返回你想要的策略实例
        return new com.netflix.loadbalancer.BestAvailableRule();
    }
}
~~~