# 哨兵模式
~~~
主从复制保证不了集群的高可用
redis提供了哨兵（Sentinel）机制来实现主从集群的自动故障恢复。
（https://blog.csdn.net/weixin_44275670/article/details/149170338?spm=1001.2101.3001.10796）

哨兵模式下，主从集群由至少三个哨兵节点（redis节点）组成的集群
    监控：Sentinel会不断检查redis集群的master和slave是否正常工作，并实时通知其他Sentinel节点。
    自动故障恢复：如果master故障，Sentinel会将一个slave提升为master。当故障回复后也以新的master为主。
    通知：Sentinel充当Redis客户端的服务发现来源，当集群发生故障转移时，会将最新信息推给Redis的客户端。


集群监控原理
    Sentinel基于心跳机制监测服务状态，每隔1秒向集群的每个实例发送ping命令：
        •主观下线：如果某sentinel节点发现某实例未在规定时间响应，则认为该实例主观下线。
        •客观下线：若超过指定数量（quorum）的sentinel都认为该实例主观下线，则该实例客观下线。quorum值最好超过Sentinel实例数量的一半。

一旦发现master故障，sentinel需要在salve中选择一个作为新的master，选择依据是这样的：
    首先会判断slave节点与master节点断开时间长短，如果超过指定值（down-after-milliseconds * 10）则会排除该slave节点
    然后判断slave节点的slave-priority值，越小优先级越高，如果是0则永不参与选举
    如果slave-prority一样，则判断slave节点的offset值，越大说明数据越新，优先级越高
    最后是判断slave节点的运行id大小，越小优先级越高。
~~~

##  脑裂问题
~~~
redis集群（哨兵模式）脑裂问题
    集群脑裂是由于主节点、从节点和Sentinel哨兵节点处于不同的网络分区，是得Sentinel哨兵没有能够心跳感知到主节点，所以通过选举的方式提升了一个节点为主，这样就有了两个master，这样
    会导致客户端还在老的主节点那里写入数据，新的节点无法同步数据，当网络回复后，Sentinel哨兵会将老的主节点将为从节点，这样再从新的master同步数据，就会导致数据丢失。

    解决方法：修改redis的配置
        min-replicas-to-write 1  最少的salve节点为1个
        min-replicas-max-lag 5  数据复制和同步的延迟不能超过5s
        设置最少的从节点数据量一级缩短主从数据同步的延迟时间，达不到要求就拒绝请求，就可以避免大量的数据丢失。

~~~

##  redis 单点 or 集群
~~~
    Redis单节点： 写操作并发8W，读操作10W

    主从（1主1从）+ 哨兵
        单节点不超过10G内存，如果Redis内存不足则可以给不同的服务分配独立的Redis主从节点（多套Redis集群）
~~~