# Redis 持久化
~~~
https://mp.weixin.qq.com/s/SYhkl6BJk77LWoUSFHfYbw
~~~

## 持久化
~~~
RDB全称Redis Database Backup file(Redis数据备份文件)，也被叫做Redis数据快照。
简单来说就是把内存中的所有数据都记录到磁盘中。当Redis实例故障重启后，从磁盘读取快照文件，恢复数据

手动备份方式
save     #由Redis主进程来执行RDB，会阻塞所有命令
bgsave   #开启子进程执行RDB，避免主进程受到影响
~~~

## 1、 Rdb 方式（Redis DataBase）
~~~
Redis 默认的方式，redis 通过Snapshot快照方式将数据持久化到磁盘中。恢复时直接从文件读到内存中。
Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到整个过程中，待持久化过程都结束，再用这个临时文件替换上次持久化的文件。
fork的作用是复制一个与当前进程一样的进程，新进程的所有数据（变量、环境变量、程序计数器）数值都和远进程一致，但是一个全新的线程，并作为原进程的子线程。
(1.1) 设置持久化快照的条件
在 redis.conf 中修改持久化快照的条件：
save 900 1              900秒（15分钟）内有1个更改   （推荐使用）
save 300 10             300秒（5分钟） 内有10个更改
save 60 10000           60秒内有10000个更改
(1.2) 持久化文件的存储目录
在 redis.conf 中可以指定持久化文件的存储目录
dbfilename dump.rdb
dir ./
(1.3) Rdb的问题
一旦redis非法关闭，那么会丢失最后一次持久化之后的数据。
如果数据不重要，则不必要关心。 如果数据不能允许丢失，那么要使用 aof 方式。

RDB的执行原理：（linux系统 进程是不能直接操作物理内存，操作系统为进程分配一个虚拟内存，操作系统会维护一个虚拟内存与物理内存的映射关系表（页表：虚拟地址和物理地址的映射关系），）
   bgsave开始的时候，主进程 会 fork 一个子进程（拷贝主进程的页表数据，子进程与主进程的映射关系相同），子进程共享主进程的内存数据。完成fork后读取内存数据并写入新RDB文件。
        fork采用的是copy-on-write技术（java中CopyOnWriteArrayList， mysql中mvcc机制）
            1.当主进程执行读操作时，访问共享数据
            2.当主进程执行写操作时，则会拷贝一份数据，执行写操作
~~~

##  2、Aof方式（Append Only File）
~~~
Redis 默认是不使用该方式持久化的。Aof 方式的持久化，是操作一次 redis 数据库，则将操作的记录存储到 aof 持久化文件中。
·第一步：开启 aof 方式持久化方案。
将redis.conf中的appendonly改为yes，即开启aof方式的持久化方案。
  appendonly yes
·Aof文件存储的目录和rdb方式的一样。 Aof文件存储的名称
 appendfilename "appendloy.aof"
 在使用aof和rdb方式时，如果redis重启，则数据从aof文件加载。
 truncated AOF 修改AOF文件里面不正确的语法
 修复：redis-check-aof --fix 进行修复
 appendfsync everysec  默认设置,异步操作,每秒记录

  数据刷盘机制
     1.always（同步刷盘），可靠性高，几乎不丢失数据   性能影响大
     2.everysec（每秒同步刷盘），性能适中，         最多丢失1秒数据
     3.no (操作系统控制)，性能高                   可靠性差，可能丢大量数据

 AOF触发重写机制： Redis会记录上次重写时的AOF大小，默认配置是当AOF文件大小是上次rewrite后大小的一倍且文件大于64M触发
~~~


##  Redis 4.0 对于持久化机制的优化
~~~
Redis 4.0 开始支持 RDB 和 AOF 的混合持久化（默认关闭，可以通过配置项 aof-use-rdb-preamble 开启）。
 如果把混合持久化打开，AOF 重写的时候就直接把 RDB 的内容写到 AOF 文件开头。这样做的好处是可以结合 RDB 和 AOF 的优点, 快速加载同时避免丢失过多的数据。
 当然缺点也是有的， AOF 里面的 RDB 部分是压缩格式不再是 AOF 格式，可读性较差。

 补充内容：AOF 重写

 AOF 重写可以产生一个新的 AOF 文件，这个新的 AOF 文件和原有的 AOF 文件所保存的数据库状态一样，但体积更小。
 AOF 重写是一个有歧义的名字，该功能是通过读取数据库中的键值对来实现的，程序无须对现有 AOF 文件进行任何读入、分析或者写入操作。
 在执行 BGREWRITEAOF 命令时，Redis 服务器会维护一个 AOF 重写缓冲区，该缓冲区会在子进程创建新 AOF 文件期间，记录服务器执行的所有写命令。
 当子进程完成创建新 AOF 文件的工作之后，服务器会将重写缓冲区中的所有内容追加到新 AOF 文件的末尾，使得新旧两个 AOF 文件所保存的数据库状态一致。
 最后，服务器用新的 AOF 文件替换旧的 AOF 文件，以此来完成 AOF 文件重写操作
~~~

## Redis 7.0.0 开始，Redis 使用Multi Part AOF机制
~~~
Multi Part AOF 就是将原来的单个 AOF 文件拆分成多个 AOF 文件。在 Multi Part AOF 中，AOF 文件被分为三种类型，
分别为：
    BASE：表示基础 AOF 文件，它一般由子进程通过重写产生，该文件最多只有一个。
    INCR：表示增量 AOF 文件，它一般会在 AOFRW 开始执行时被创建，该文件可能存在多个。
    HISTORY：表示历史 AOF 文件，它由 BASE 和 INCR AOF 变化而来，每次 AOFRW 成功完成时，本次 AOFRW 之前对应的 BASE 和 INCR AOF 都将变为 HISTORY，HISTORY 类型的 AOF 会被 Redis 自动删除。
~~~

## AOF 为什么是在执行完命令之后记录日志？
~~~
关系型数据库（如 MySQL）通常都是执行命令之前记录日志（方便故障恢复），而 Redis AOF 持久化机制是在执行完命令之后再记录日志。

为什么是在执行完命令之后记录日志呢？
    避免额外的检查开销，AOF 记录日志不会对命令进行语法检查；
    在命令执行完之后再记录，不会阻塞当前的命令执行。
    
这样也带来了风险（我在前面介绍 AOF 持久化的时候也提到过）：
    如果刚执行完命令 Redis 就宕机会导致对应的修改丢失；
    可能会阻塞后续其他命令的执行（AOF 记录日志是在 Redis 主线程中进行的）。
~~~

## AOF 重写
~~~
当 AOF 变得太大时，Redis 能够在后台自动重写 AOF 产生一个新的 AOF 文件，这个新的 AOF 文件和原有的 AOF 文件所保存的数据库状态一样，但体积更小。


~~~


## 生产环境监控建议：
~~~
# 监控 AOF rewrite 状态
redis-cli INFO persistence | grep aof_rewrite_in_progress

# 监控 AOF 文件大小增长
redis-cli INFO persistence | grep aof_current_size
redis-cli INFO persistence | grep aof_base_size

# 检查磁盘和 inode 使用率
df -h /var/lib/redis
df -i /var/lib/redis

# 设置 AOF rewrite 期间增量 fsync 策略（Redis 7.0+）
# aof-rewrite-incremental-sync yes


# 完整生产配置示例
appendonly yes
aof-use-rdb-preamble yes

# 性能优化
aof-rewrite-incremental-fsync yes # 增量 fsync，减少磁盘 I/O 峰值
# 延迟敏感场景（推荐 yes）
no-appendfsync-on-rewrite yes # 重写期间暂停 fsync，避免阻塞
# 数据安全场景（推荐 no）
no-appendfsync-on-rewrite no # 重写期间仍执行 fsync，可能阻塞但更安全

# 容量规划建议：
# - 预留 2x 内存作为磁盘空间
# - 保持单个 AOF 文件 < 16GB
# - 监控 aof_delayed_fsync 指标
~~~
