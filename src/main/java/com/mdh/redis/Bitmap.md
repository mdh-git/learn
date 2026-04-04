# Bitmap

## Bitmap 核心特性
~~~
Bitmap(位图)本质上是 Redis String 类型的扩展，每个 bit 位表示 0/1 状态，支持位级操作。其优势在于:
    极致空间效率:1亿用户签到仅需约12MB
    高效位运算:支持 AND/OR/XOR/NOT 等原子操作
    O(1)时间复杂度:SETBIT/GETBIT 操作均为常数时间
~~~

## 核心使用场景
~~~
每日签到系统
    SETBIT sign:2023:10:01 10086 1      #用户ID 10086 签到
    NBITCOUNT sign:2023:10:01           #统计当日签到人数
    BITFIELD sign:2023:10:01 GET u30 10086    #获取连续签到情况
    
用户活跃度分析
    记录用户每月活跃天数
    BITOP OR monthly_active user:day:01 user:day:02 ... user:day:31
    BITCOUNT monthly active #月活跃用户数
~~~
~~~
实时数据统计
    DAU/MAU 统计
        #每日活跃用户(每个bit代表一个用户)一
        SETBIT dau:2023-10-01 5001 1
        BITOP OR weekly active dau:2023-10-01 ... dau:2023-10-07


留存率计算
    #计算次日留存(通过AND运算)I
    BITOP AND retention dau:2023-10-01 dau:2023-10-02
    BITCOUNT retention
~~~
~~~
布隆过滤器

#使用多个哈希函数模拟布隆过滤器
    MULTI
    SETBIT bloom:filter hash1(user id)1
    SETBIT bloom:filter hash2(user id)1
    SETBIT bloom:filter hash3(user id)1
    EXEC

#检查存在性(需所有位都为1)
    GETBIT bloom:filter hash1(new user)
~~~
~~~
权限管理系统

#权限位映射(1:读2:写4:执行8:删除)
    SETBIT user:permissions:1000101    #开启读权限
    SETBIT user:permissions:1000111    #开启写权限
    BITOP AND can write user:permissions:10001 write mask
~~~
~~~
特征标签系统

#用户画像标签(每个标签一个bit)
    SETBIT tags:gender:male 10001 1
    SETBIT tagsrvip 10001 1
    SETBIT tags:location:beijing 10001 1

#多标签组合查询
    BITOP AND target users tags:gender:male tags:vip
~~~
~~~
时间序列数据压缩

#监控数据记录(每分钟一个bit)
    SETBIT server:status:8080 1440 1    #第1440分钟正常了
    BITCOUNT server:status:8080 0-1     #统计正常时长
~~~
~~~
去重计数

#UV统计(非精确但高效)
    SETBIT uv:page:home 20230915 1
    PFCOUNT uv:page:home #结合HyperLogLog使用
~~~


## 场景
~~~
推荐场景
    用户ID连续或可映射为整数
    数据量大但值域范围可控
    需要快速集台运算
    状态只有两种(是/否)
    

使用限制
    #内存使用示例(需预评估):
    #10亿用户签到  →1  0^9/8/1024^2 = 119MB
    #偏移量最大 2^32-1
~~~

## 性能优化技巧
~~~
批量操作管道化
    PIPELINE
    SETBIT user:actions 1000 1
    SETBIT user:actions 1001 1
    EXEC
    
冷热数据分离
    #热数据使用Bitmap，冷数据归档到数据库
    BITOP OR archive:2023:01 bitmap:jan bitmap:feb bitmap:mar
    
结合其他数据结构
    # Bitmap +Sorted set 实现精确统计
    ZADD user:login:times 152 user:1001
    SETBIT user:login:days 1001 1
~~~



## 典型案例
~~~
案例1:电商大促活动
    #1.用户资格校验(白名单)
    SETBIT promotion:whitelist 100001 1

    #2.活动参与记录
    SETBIT promotion:participants:day 1100001 1

    #3.奖励发放去重
    BITOP OR rewarded users day1 users day2 users
    
    
案例2:游戏在线系统
    #实时在线玩家(每5分钟更新)
    SETBIT online:shard1 2023041512001

    #跨服匹配(AND找共同空闲玩家)
    BITOP AND available players online:shard1 idle:shard1
    
    
案例3:广告投放系统
    #用户兴趣标签组合
    BITOP AND target audience tag:tech tag:male tag:age 25 35

    #广告曝光去重(24小时内)
    SETBIT ad:123:2023-10-01 100011 EX 86400
~~~

## 监控与维护
~~~
1.内存监控
    INFO memory #监控used memory
    MEMORYUSAGEkeyname #查看具体Key内存
2.碎片整理
    #Bitmap自动扩展可能产生碎片
    MEMORY PURGE#4.0+版本支持
3.备份策略
    #RDB快照+AOF重写
    CBGSAVE
    BGREWRITEAOF
~~~