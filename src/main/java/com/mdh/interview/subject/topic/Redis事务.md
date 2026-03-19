# Redis事务

## 事务介绍
~~~
Redis的事务是通过MULTI，EXEC，DISCARD和WATCH这四个命令来完成。
Redis的单个命令都是原子性的，所以这里确保事物的对象是命令集合。
Redis将命令集合序列化并确保处于一事务的命令集合连续且不被打断的执行。
Redis不支持回滚的操作。
~~~

## MULTI
~~~
用于标记事务块的开始
Redis会将后续的命令逐个放入队列中，然后使用EXEC命令原子化地执行这个命令序列。
~~~

## EXEC
~~~
在一个事务中执行所有先前放入队列的命令,然后恢复正常的链接状态
~~~

## DISCARD
~~~
清楚所有先前在一个事务中放入队列的命令，然后恢复正常的连接状态。
~~~

## WATCH
~~~
当某个事务需要按照条件执行时，就要使用这个命令讲给定的键设置为受监控的状态。
该命令可以实现redis的乐观锁
~~~

## UNWATCH
~~~
清除所有先前为一个事务监控的键。
~~~

## Redis分布式锁
~~~

public static final String REDIS_LOCK = "REDIS_LOCK";

String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

try {
    // 保证原子操作 加锁和设置时间在同一个操作中执行
    stringRedisTemplate.opsForValue.setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECINDS);
} finally {

}
~~~

## 使用事务删除redis的分布式锁
~~~
官网建议使用lua脚本

使用事务方法
finally {
    while(true) {
        // 开启监控
        stringRedisTemplate.watch(REDIS_LOCK);
        if(stringRedisTemplate.opsForValue().get(REDIS_LOCK).equalsIgnoreCase(value)){
            // 开启事务
            stringRedisTemplate.setEnableTransactionSupport(true);
            stringRedisTemplate.multi();
            stringRedisTemplate.delete(REDIS_LOCK);
            List<Object> list = stringRedisTemplate.exec();
            if(list == null) {
                continue;
            }
        }
        stringRedisTemplate.unwatch();
        break;
    }
}
~~~

## 使用lua脚本删除redis的分布式锁
~~~
finally {
    Jedis jedis = new Jedis("localhost", 6379);

    String script = "if redis.call('get', KEY[1] == ARG[1])" +
            "then " +
            "return redis.call('del', KEY[1])" +
            "else " +
            " return 0 " +
            "end";

    try {
        Object o = jedis.eval(script, Collections.singletonList(REDIS_LOCK), Collections.singletonList(value));
        if("1".equals(o,toString())){
            // del redis lock ok
        } else {
            // del redis lock error
        }
    }finally {
        if(null != jedis){
            jedis.close();
        }
    }
}

~~~

## 使用Redisson实现分布式锁
~~~
避免集群环境下出现的问题

优点：
1. Redisson 通过 Watch Dog 机制很好的解决了锁的续期问题。
2. 和 Zookeeper 相比较，Redisson 基于 Redis 性能更高，适合对性能要求高的场景。
3. 通过 Redisson 实现分布式可重入锁，比原生的 SET mylock userId NX PX milliseconds + lua 实现的效果更好些，虽然基本原理都一样，但是它帮我们屏蔽了内部的执行细节。
4. 在等待申请锁资源的进程等待申请锁的实现上也做了一些优化，减少了无效的锁申请，提升了资源的利用率。


缺点：
1、使用 Redisson 实现分布式锁方案最大的问题就是如果你对某个 Redis Master 实例完成了加锁，此时 Master 会异步复制给其对应的 slave 实例。
但是这个过程中一旦 Master 宕机，主备切换，slave 变为了 Master。接着就会导致，客户端 2 来尝试加锁的时候，在新的 Master 上完成了加锁，而客户端 1 也以为自己成功加了锁，
此时就会导致多个客户端对一个分布式锁完成了加锁，这时系统在业务语义上一定会出现问题，
导致各种脏数据的产生。所以这个就是 Redis Cluster 或者说是 Redis Master-Slave 架构的主从异步复制导致的 Redis 分布式锁的最大缺陷
（在 Redis Master 实例宕机的时候，可能导致多个客户端同时完成加锁）（后面有Redission红锁可以解决）

~~~
~~~
RLock lock = redisson.getLock(REDIS_LOCK);
lock.lock();
try {

} finally {
    // 在高并发的情况下会出现异常
    // 抛出错误异常: attempt to unlock lock, not locked by current thread by node id
    lock.unlock();
}

使用下面的方式
RLock lock = redisson.getLock(REDIS_LOCK);
lock.lock();
try {

} finally {
    // 还在锁定状态  &&  当前线程持有自己的锁
    if(lock.isLocked() && lock.isHeldByCurrentThread()){
        lock.unlock();
    }
}
~~~

## Redis内存调整
~~~
如果不设置最大内存大小或者设置最大内存大小为0，在64位操作系统中不限制内存的大小

在redis的配置文件redis.conf中设置maxmemory(单位：字节)

命令
config get maxmemory
config set maxmemory value

查看redis内存使用情况
info memory

当redis存满的时候，再写数据会报OOM
(error) OOM command not allowed when used memory > 'maxmemory'
~~~

## watchDog 使用
~~~
watchDog 只有在未显示指定加锁时间时才会生效。
watch dog 在当前节点存活时每10s给分布式锁的key续期 30s；


因为无论在释放锁的时候，是否出现异常，都会执行释放锁的回调函数，把看门狗停了
有没有设想过一种场景？服务器宕机了？
其实这也没关系，首先获取锁和释放锁的逻辑都是在一台服务器上，那看门狗的续约也就没有了，redis中只有一个看门狗上次重置了30秒的key，
时间到了key也就自然删除了，那么其他服务器，只需要等待redis自动删除这个key就好了，也就不存在死锁了


lockWatchdogTimeout（监控锁的看门狗超时，单位：毫秒）
默认值：30000

监控锁的看门狗超时时间单位为毫秒。
该参数只适用于分布式锁的加锁请求中未明确使用leaseTimeout参数的情况。
如果该看门狗未使用lockWatchdogTimeout去重新调整一个分布式锁的lockWatchdogTimeout超时，那么这个锁将变为失效状态。
这个参数可以用来避免由Redisson客户端节点宕机或其他原因造成死锁的情况。

1.要使 watchLog机制生效 ，lock时 不要设置 过期时间
2.watchlog的延时时间 可以由 lockWatchdogTimeout指定默认延时时间，但是不要设置太小。如100
3.watchdog 会每 lockWatchdogTimeout/3时间，去延时。
4.watchdog 通过 类似netty的 Future功能来实现异步延时
5.watchdog 最终还是通过 lua脚本来进行延时

https://blog.csdn.net/weixin_51146329/article/details/129612350
https://blog.csdn.net/justlpf/article/details/130677262
~~~

## watchdog 失效场景
~~~
https://mp.weixin.qq.com/s/DztNJzpGA-pGesQQuofEtg

看门狗机制的健壮性依赖于客户端进程的健康、网络连接的稳定以及 Redis 服务的可靠性。
任何环节的薄弱都可能导致 Watchdog 续期失败。

1. 进程内部故障：Watchdog 任务的生命线断裂
    看门狗通常是应用程序内部的一个后台线程或异步协程。它的存活与主应用进程的健康状态紧密相连。

    CPU 调度饿死： 如果主应用程序由于极端高负载、资源争抢或长时间持有关键同步锁，导致 Watchdog 线程无法获得足够的 CPU 调度时间，它就无法在 TTL 到期前发出续期请求。
    应用程序崩溃或OOM： 当主应用程序遭遇致命错误（如 Out Of Memory 导致 JVM 崩溃，或 Rust/Go 应用程序的 panic），整个进程被操作系统终止，Watchdog 任务也随之消亡。
    
2. 网络与 Redis 通信故障：续期链路的中断
    续期本身是一个原子性的网络 I/O 操作。通信链路上的任何问题都可能导致续期失败。

    网络分区或瞬时中断： 客户端与 Redis 之间网络连接瞬时中断或长时间断开，续期请求返回网络错误。
    Redis 负载过高： Redis 服务器压力山大，导致续期命令响应延迟急剧升高，锁在续期成功前就过期。
    Redis 集群故障转移： 在 Redis Sentinel 或 Cluster 架构中，如果发生主节点宕机并触发故障转移（Failover），在选举新主节点的几秒钟窗口期内，续期请求可能被延迟或拒绝处理，导致锁过期。
    Redis 内存淘汰： 极端情况下，如果 Redis 内存不足，可能触发 Keys 淘汰策略，将锁 Key 作为 LRU/LFU 目标清除，即使 Watchdog 仍在运行，也无法找到 Key 进行续期。

3. 逻辑与时序错误：意料之中的“失败”
    并非所有 Watchdog 的停止都是“故障”，有些是机制的正常安全退出。

    锁值不匹配（预期失败）： 当 Watchdog 周期性尝试续期时，它会使用 Lua 脚本进行原子性续期。
        如果 Lua 脚本发现当前 Redis 中存储的锁 Key 的 Value 已经不是自己当初设置的唯一 ID（因为锁已被其他客户端 B 抢走），Watchdog 必须主动停止。
        锁过期（这表明锁已不再由本客户端持有，继续续期将是危险的。
        
        
Watchdog 失败导致的锁过期，是分布式锁设计中一个不可避免的风险点。 
我们通过合理设置 TTL、周期性续期和重试机制来最大限度地避免锁提前释放；
通过 Lua 脚本来阻止误删。
然而，保障最终数据一致性的责任，最终必须由上层的分布式事务框架（如 TCC/SAGA）和业务逻辑自身的防御性编程（如乐观锁、数据版本校验）来承担。 
~~~