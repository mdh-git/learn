# 主从复制
~~~
(1)主从复制
持久化保证了即使redis服务重启也不会丢失数据，因为redis服务重启后会将硬盘上持久化的数据恢复到内存中，
但是当redis服务器的硬盘损坏了可能会导致数据丢失，如果通过redis的主从复制机制就可以避免这种单点故障
                            主Redis
                            (master)
                      /                   \
                  /                          \
           从 Redis1                       从 Redis2
           (slave)                          (slave)

说明:
  ·主redis中的数据有两个副本（replication）即从redis1和从redis2，即使一台redis服务器宕机其它两台redis服务也可以继续提供服务。
  ·主redis中的数据和从redis上的数据保持实时同步，当主redis写入数据时通过主从复制机制会复制到两个从redis服务上。
  ·只有一个主redis，可以有多个从redis。
  ·主从复制不会阻塞master，在同步数据时，master 可以继续处理client 请求
  ·一个redis可以即是主又是从
                              主Redis
                              (master)
                      /                   \
                  /                          \
             从 Redis1                       从 Redis2
             (slave)                          (slave)
         /              \
    /                       \
从 Redis3                从 Redis4
(slave)                  (slave)

(2)主从复制设置
(2.1) 主机配置
      无需配置
(2.2) 从机配置
   ·第一步：复制出一个从机 cp bin/ bin2 -r
   ·第二步：修改从机的 redis.conf 语法：slaveof masterip masterport
                                         slaveof 192.168.242.137 6379
   ·第三步：修改从机的 port 地址为 6380
                        port 6380
   ·第四步：清除从机的持久化文件 rm -rf appendonly.aof dump.rdb
   ·第五步：启动从机 ./redis-server redis.conf
   ·第六步：启动6380的客户端 ./redis-cli -p 6380
   注意:（主机一旦发生增删改操作，那么从机会将数据同步到从机中 从机不能执行写操作）


info replication 查看当前redis的信息
slavof host port  设置当前slave对应的master
复制延迟

方法:
(1) 一主二仆
(1) 薪火相传
(1) 反客为主
(4) 哨兵模式（投票选举）
    最常用，添加(touch)    sentinel.conf
    编辑(vi)  sentinel.conf  加入  sentinel monitor (自定义的名字)  master的host  port  1(投票最多的)


~~~

## 主从同步原理
~~~
主从数据同步原理： 参考：https://blog.csdn.net/weixin_44275670/article/details/149151848
    1.主从全量同步
        Replication Id:简称replId，是数据集的标记，id一致则说明是同一数据集。每一个master都有一个replId，slave则会继承master节点的replId。
        offset：偏移量，随着记录在repl_backLog中的数据增多而逐渐增大。slave完成同步时也会记录当前同步的offset。
        如果slave的offset小于master的offset，说明slave数据落后于master，需要更新。

    步骤：
        1.slave执行replicaof命令，连接链接到master。
        2.请求同步数据（带上 replId、offset）。
        3.master 判断是否是第一次同步（判断replId是否一致：不一致证明是全新的slave，要做全量同步）。
        4.master 是第一次，返回master的数据版本信息（replId、offset）。
        5.slave 保存接收master版本信息。
        6.master 执行bgsave，生成RDB文件。
        7.master 将RDB文件发送给slave。
        8.slave 清空本地数据，加载RDB文件。
        9.master 记录RDB期间的所有新的命令。
        10.master 发送repl_backLog中的命令。
        11.slave 执行接收repl_backLog中的命令。

    2.主从增量同步（slave重启或后期数据变化）
        1.slave 重启。
        2.slave psync replId offset 到master。
        3.master 判断请求replId是否一致。
        4.master 不是第一次，返回continue。
        5.master 去repl_bakLog中获取offset后的数据。
        6.master 发送offset后的命令。
        7.slave 执行offset后的命令。

repl_backlog原理：
    repl_baklog文件是一个固定大小的数组，只不过数组是环形，也就是说角标到达数组末尾后，会再次从0开始读写，这样数组头部的数据就会被覆盖。




主从同步优化
    主从同步可以保证主从数据的一致性，非常重要。

可以从以下几个方面来优化Redis主从就集群：
    在master中配置repl-diskless-sync yes启用无磁盘复制，避免全量同步时的磁盘IO。
    Redis单节点上的内存占用不要太大，减少RDB导致的过多磁盘IO
    适当提高repl_baklog的大小，发现slave宕机时尽快实现故障恢复，尽可能避免全量同步
    限制一个master上的slave节点数量，如果实在是太多slave，则可以采用主-从-从链式结构，减少master压力


全量同步和增量同步区别？
    全量同步：master将完整内存数据生成RDB，发送RDB到slave。后续命令则记录在repl_baklog，逐个发送给slave。
    增量同步：slave提交自己的offset到master，master获取repl_baklog中从offset之后的命令给slave

什么时候执行全量同步？
    slave节点第一次连接master节点时
    slave节点断开时间太久，repl_baklog中的offset已经被覆盖时
什么时候执行增量同步？
    slave节点断开又恢复，并且在repl_baklog中能找到offset时
~~~