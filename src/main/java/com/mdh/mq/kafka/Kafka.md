
# Kafka

## Kafka是如何保证消息不丢失
~~~
丢失消息场景
1.生产者发送消息到brocker丢失
    1.设置异步发送，发送失败使用回调进行记录或重发
    2.消息失败重试，参数设置，可以设置重试次数
2.消息在brocker中存储丢失
    1.发送确认机制acks，选择all，让所有的副本都参与保存数据后确认
3.消费者从brocker接收消息丢失
    禁止自动提交偏移量，改为手动
    1.同步提交
    2.异步提交
    3.同步+异步组合提交
~~~

## kafka中消息的重复消费解决
~~~
1.关闭自动提交偏移量，开启手动提交偏移量
2.提交方式，最好同步+异步提交
3.幂等方案（token+redis）
~~~

## kafka如果保证消息的顺序性
~~~
问题原因:
    一个topic的数据可能存储在不同的分区中，每个分区都有一个按照顺序的存储的偏移量，如果多个消费者关联了多个分区不能保证顺序性。
解决方案：
    1.发送消息时指定分区号
    2.发送消息时按照相同的业务设置相同的key

// 指定分区
kafkaTemplate.send(topic, partition, key, data) 即partition为同一个
// 相同的业务key
kafkaTemplate.send(topic, key, data) 即key相同
~~~

## kafka高可用
~~~
集群：
    一个kafka集群是由多个broker实例组成，即使某一台broker宕机，集群仍然可以正常工作。
复制机制：
    一个topic由多个分区，每个分区有多个副本，有一个leader，其余的是follower，副本存储在不同的broker中
    所有的分区副本的内容是相同，如果leader发生故障时，会自动将其中一个follower提升为leader，保证了系统的容错性、高可用性
    
    
复制机制中的ISR(In-Sync Replica)：需要同步复制保存的follower
分区副本分为两类，一个是ISR，与leader副本同步保存数据
              另一个是普通的副本，是异步保存数据
              当leader挂掉之后，会优先从ISR副本列表中选取一个作为leader
~~~

## kafka数据清理机制
~~~
kafka文件存储机制
    1.kafka中topic的数据存储在分区上，分区如果文件过大会分段存储segment
    2.每个分段都在磁盘上以索引（XXX.index）和日志文件（xxx.log）的形式存储
    3.分段的好处是：第一本报告减少单个文件内容的大小，查找数据方便。 第二：方便kafka进行日志清理
    
数据清理机制
    1.根据消息的保留时间，当消息在kafka中保存的时间超过指定的时间，就会触发清理过程
        log.retention.hours=168  // 168小时
    2.根据topic存储的数据大小，当topic所占的日志文件打下大于一定阈值，则开始删除最久的消息。需要手动启动
        log.retention.bytes=1073741824
~~~