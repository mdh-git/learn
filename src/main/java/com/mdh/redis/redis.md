# Redis

## 特性
~~~
1.内存存储，极致速度:数据主要在内存中，读写性能极高(可达10万+/秒 QPS)。
2.丰富的数据结构:支持 Strings, Hashes, Lists,Sets,Sorted Sets,Bitmaps,HyperLogLogs用于高效地进行基数统计(如统计独立访客数)，Streams等，而不仅仅是简单的 Key-Value。
3.单线程与原子性:命令操作是原子的，无需担心并发竞争，特别适合计数器、状态同步等场景。
4.持久化可选:支持 RDB 快照和 AOF 日志，能在性能与数据安全间取得平衡。
5.发布订阅与Lua脚本:支持消息通信和复杂原子逻辑。
~~~

## String（字符串）
~~~
存储结构: key - value形式
使用场景: 
    // 缓存用户信息
    redis.set("user:1001","{\"name\":\"张三\", \"age\":25}")
    // 文章阅读数+1
    redis.incr("page:view:1001")
        
    缓存:存储用户信息、配置信息
    计数器:INCR、DECR命令实现访问计数
    分布式锁:SETNX命令实现访问计数
    Session共享:存储用户会话信息
~~~

## Hash(哈希)
~~~
存储结构:field-vale 组成的map,适合存储对象
使用场景：
    // 存储用户信息
    redis.hset("user：1001"，"name","张三");
    redis.hset("user：1001"，"age","25");
    // 购物车商品数量+1
    redis.hincby("cart:1001","product:5001",1);
    
    存储对象：用户信息，商品信息
    购物车：用户ID为key，field为商品，value为数量
    配置分组：相关配置项放在同一个hash中
~~~

## List(列表)
~~~
存储结构：双向链表、按插入顺序排序
使用场景：
    // 消息队列
    redis.lpush("queue:task","task_data");
    string task = redis.brpop("queue:task",30); 阻塞获取
    
    消息队列：LPUSH + BRPOP实现简答队列
    最新列表：朋友圈动态、最新文章
    历史记录：用户搜索历史、浏览记录
~~~

## Set(集合)
~~~
存储结构：无序、不重复的字符串集合
使用场景：
    // 共同关注
    redis.sadd("user:1001:follow","2001","2002","2003");
    redis.sadd("user:1002；follow","2001","2003","2004");
    Set<String> common = redis.sinter("ser:1001:follow","user:1002；follow")  共同关注
    
    标签系统：用户标签、文章标签
    共同好用、关注：SINTER求交集
    抽奖活动：SRANDMEBER随机抽取
~~~

## Sorted Set(有序集合)
~~~
存储结构： Set基础上为每一个元素关联一个分数(score),用于排序
使用场景：
    // 游戏排行榜
    redis.zadd("game:rank",5000,"player1");
    redis.zadd("game:rank",6000,"player2");
    Set<String> top3 = redis.zrevrage("game:rank",0,2); //前三名 

    排行榜：游戏积分榜、搜索榜
    延迟队列：用时间戳作为score
    范围查找：按分数范围查询
~~~

## Bitmaps (位图)
~~~
本质：String类型的位操作，最大512MB
使用场景：
    // 用户签到
    redis.setbit("sign:2023:20:1001",15,1); // 签到
    Long count = redis.bitcount("sign:2023:20:1001"); // 本月签到次数
    
    用户签到：每天对应一个bit
    活跃用户统计
    布隆过滤器 
~~~

## HyperLogLog 
~~~
特点：用于基数统计、固定使用12kb内存、有0.81误差
使用场景;
    // 统计UV
    redis.pfadd("uv:2023:10:16","user1","user2","user3");
    Long uv = redis.pfcount("uv:2023:10:16"); // 估算UV
    
    UV统计：统计独立访问客数
    大规模去重计数
~~~


## BigKey
~~~
https://cloud.tencent.com/developer/news/918934

大key: key越长，内存占用越高，查询效率可能下降

1.使用redis自带的命令进行扫描  
    redis-cli -- bigkeys
        会以采样的方式，扫描整个实例，统计每种类型中最大的key，并给出大致大小，快速定位到大key
        key占用的空间 MEMORY USAGE key 命令， 用于精确查询某个具体 Key 占用了多少字节
        
2.使用 SCAN 命令来查找大 Key（BigKey）是生产环境中最安全、最推荐的做法
    SCAN 采用游标（Cursor）机制进行分批次、渐进式遍历，不会阻塞服务器。

监控工具分析


解决大value:
    分片存储: 将大Hash拆分成多个小Hash(如 user:1001:info1 , user:1001:info2)
    数据压缩: 对JSON/文本使用GZIP压缩（需权衡CPU开销）
    冷热分离: 大key主要是历史数据，访问频率很低，没必要一直放在redis，可以迁移到别的存储
    
~~~

~~~
1.关于 COUNT 参数：
    SCAN 的 COUNT 参数只是一个提示，并不代表每次一定返回这么多。如果 Redis 内部正在进行 Rehash，或者数据分布不均，返回的数量可能会少于 COUNT。

2.数据不一致性：
    SCAN 是非阻塞的，这意味着在扫描过程中，数据可能会发生变化。
        重复：同一个 Key 可能会被返回多次（需要在代码中去重）。
        遗漏：在扫描期间被删除的 Key 可能不会被返回，或者新增加的 Key 可能不会被扫描到。
        但在查找大 Key 的场景下，这种微小的不一致通常是可以接受的。

3.不要使用 KEYS 命令：
    千万不要在生产环境使用 KEYS * 来查找大 Key。如果 Redis 中有几百万个键，KEYS * 会阻塞主线程数秒甚至数分钟，导致线上服务直接“假死”。

4.内存占用计算：
    MEMORY USAGE 命令返回的是 Key 的总内存占用（包含键名、值、数据结构开销），这比单纯看 Value 的长度（如 STRLEN 或 LLEN）更准确。
~~~



## redis监控命令
~~~
// 大key扫描（阻塞式,谨慎使用）
redis-cli --bigkeys


// 热key监控（需提前开启）
redis-cli --hotkeys
需配置： cofig set maxmemory-policy allkeys-lru
~~~

## Redis内置命令
~~~
查看key内存占用
redis-memory-for-key <keyname>

慢查询分析
slowlong get 10
~~~

## 运营工具
~~~
Redis-rdb-tools 分析RDB文件识别大key

Redis-Faina ： 基于MONITOR命令的实时分析工具
商业监控： 阿里云Redis控制台、腾讯云DBA工具
~~~


## 热key问题解决方案
~~~
1.增加本地缓存
    使用Guava/Caffeine在应用层缓存热Key
    设置合理的TTl，避免数据不一致
    
2.key拆片与分片
    原始热Key:user:1000:profile
        拆分为:
        user:1000:profile:part1
        user:1000:profile:part2
    客户端通过hash算法访问不同分片，压力分配到不同的机器
    
3，Redis集群分片优化
    对热Key使用随机后缀分散到不同节点
        热Key添加随机后缀
        hot:key:{frandom suffix}
        
4.读写分离与副本扩展
    为热Key所在节点增加只读副本
    客户端轮询访问不同副本
    
5.二级缓存架构
    客户端→CDN/边缘缓存→应用本地缓存→Redis集群
~~~

## 大Key问题解决方案
~~~
1.数据拆分
    String类型:拆分为多个Key，使用MGET获取
    Hash/Set/ZSet:按field范围或hash分桶

    大Hash拆分示例
    user:1000:profile:base info
    user:1000:profile:extended info
    user:1000:profile:preferences4

    分桶策略
    user:1000:orders:bucket {hash(field)%10}
    
2.数据结构优化
    压缩存储数据(MessagePack、Protobuf)
    使用HyperLogLog替代大Set做基数统计
    使用Bitmap替代Boolean列表
    
3.异步处理与惰性删除
//异步删除大Key
public void asyncDeleteBigKey(String key){
    executor.submit(()->
        //使用SCAN分批删除
        String cursor ="";
        do{
            ScanResult<String>scanResult=redis.scan(
                cursor,
                new ScanOptions.ScanOptionsBuilder()
                    .match(key + ":*")
                    .count(188)
                    .build()
        );
        cursor =scanResult.getCursor();
        redis.delete(scanResult.getResult().toArray(new String[0]));
        }while(!"）".equals(cursor));
    });
}

4.数据生命周期管理
    设置合理的TTL自动过期
    定期清理历史数据
    
5.容量规划
    监控内存增长趋势
    提前扩容，避免内存达到maxmemory
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
        碎片整理：  碎片清理、开启 active-defrag yes,让redis在后台整理内存碎片
        集中过期：防止同一时间过期，增加随机时间 redis.expireat(key, expire_time + random(300))
        
    性能优化：
        Pipeline：批量操作（如一次性导入数据）使用 Pipeline 减少 RTT（网络往返时间），QPS 可提升数倍。
        持久化策略：根据业务容忍度选择 RDB（快照，恢复快但丢数据）或 AOF（追加日志，数据全但文件大），通常混合使用。
    
    高可用架构：
        掌握 Redis Sentinel（哨兵）或 Redis Cluster（集群）的部署与故障转移原理。
~~~
