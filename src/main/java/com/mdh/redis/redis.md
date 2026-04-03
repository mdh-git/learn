# Redis

## BigKey
~~~
https://cloud.tencent.com/developer/news/918934

~~~

## 字典（Dictionary）
~~~

https://cloud.tencent.com/developer/article/1965428

字典（Dictionary），也被称为哈希表（Hash Table），是一种用于存储键值对（Key-Value Pair）的核心数据结构。
~~~

## 底层实现原理
~~~
Redis 的字典是使用哈希表来实现的

1.哈希表 (dictht)
这是字典的骨架，包含一个 table 数组。数组中的每个元素都是一个指向 dictEntry 节点的指针。table 数组的大小（size）是 2 的 n 次方，
used 记录了已使用的节点数量。
~~~

## Redis优化
~~~
使用场景
    1.缓存加速
    2.分布式锁
    3.多样的数据结构  string hash list set zset
            ZSet：实现排行榜、延迟队列（以时间戳为 score）。
            Bitmap/HyperLogLog：海量用户签到、UV 统计（极省内存）。   
            List/Stream：简单的消息队列。
 
优化策略：
    内存优化：
        拒绝BigKey：一个大 Key（如包含百万字段的 Hash）会导致网络阻塞和删除时主线程卡顿。需拆分或使用 SCAN 渐进式删除。
        数据结构选型：例如存对象用 Hash 比 String 省内存（约 30%+）；签到用 Bitmap 仅需 125KB 即可存 100 万人数据。
        内存淘汰策略：生产环境必须配置 maxmemory 和 maxmemory-policy（如 allkeys-lru），防止 OOM 导致服务宕机。
        
    性能优化：
        Pipeline：批量操作（如一次性导入数据）使用 Pipeline 减少 RTT（网络往返时间），QPS 可提升数倍。
        持久化策略：根据业务容忍度选择 RDB（快照，恢复快但丢数据）或 AOF（追加日志，数据全但文件大），通常混合使用。
    
    高可用架构：
        掌握 Redis Sentinel（哨兵）或 Redis Cluster（集群）的部署与故障转移原理。
~~~
