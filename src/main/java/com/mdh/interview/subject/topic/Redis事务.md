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