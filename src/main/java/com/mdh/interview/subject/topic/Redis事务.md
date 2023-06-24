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
但是这个过程中一旦 Master 宕机，主备切换，slave 变为了 Master。接着就会导致，客户端 
2 来尝试加锁的时候，在新的 Master 上完成了加锁，而客户端 1 也以为自己成功加了锁，此时就会导致多个客户端对一个分布式锁完成了加锁，这时系统在业务语义上一定会出现问题，
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