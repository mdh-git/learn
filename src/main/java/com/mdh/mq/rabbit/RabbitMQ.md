# MQ

## 场景
~~~
三点： 解耦、异步、削峰
异步发送（验证码、短信、邮件）
Mysql和redis、es之前的数据同步
分布式事务
削峰填谷
~~~

## RabbitMQ保证消息不丢失
~~~
RabbitMQ：
    消息丢失场景：
        1.生产者生效消息没有到达交换机或者没有到达队列
        2.MQ宕机导致队列消息丢失
        3.消费者服务未收到消息或者宕机

一、生产者确认机制：（ack）
    RabbitMQ提供了publisher confirm机制来避免消息发送到MQ过程中丢失。消息发送到MQ以后，会返回一个结果给发送者，表示消息是否处理成功。
    失败：消息发送到交换机失败 （nack publish-confirms）
         交换机路由到队列失败 （ack publish-return）

    消息失败之后处理？
        1.回调方法即时重发
        2.记录日志
        3.保存到数据库然后定时重发，成功发送后即刻删除表中的数据
    
二、消息持久化
    MQ默认是内存存储消息，开启持久化功能可以确保缓存在MQ中的消息不丢失。
    
    1.交换机持久化
    2.队列持久化
    3.消息持久化，SpringAMQP中的消息默认是持久化的，可以通过MessageProperties中的DeliveryMode来制定
    
三、消费者确认
    RabbitMQ支持消费者确认机制，消费者处理消息后可以向MQ发送ack回执，MQ收到ack回执之后才会删除该消息。
    SpringAMQP允许配置三种确认模式：一般使用自动ack
        manual：手动ack，需要再业务代码结束后，调用api发送ack。
        auto：自动确认，由spring监测listener代码是否出现异常，没有异常则返回ack，抛出异常则返回nack。
        none：关闭ack，MQ假定消费者获取消息后会成功处理，因此消息投递后立即被删除
        
    利用Spring的retry机制，在消费者出现异常时利用本地充实，设置重试次数，当次数达到了以后，如果消息依然失败，则将消息投递到异常交换价，交由人工处理
    
    
总结：
    开启生产者确认机制，保证生产者的消息能到达队列
    开启持久化功能，确保消息为消费前在队列中不会丢失
    开启消费者确认机制为auto，有Spring确认消息处理成功后完成ack
    开启消费者失败重试机制，多次重试失败将消息投递到异常交换机，由人工处理
~~~

## RabbitMQ消息的重复消费问题？
~~~
产生原因: 网络抖动、消费者挂了

解决方案：
    1.每条消息设置一个唯一的标识id（支付id、订单id）
    2.幂等方案：分布式锁、数据库锁（悲观锁、乐观锁）
~~~

## RabbitMQ的死信交换机？（RabbitMQ延迟队列）
~~~
延迟队列： 进入队列的消息会被延迟消费的队列
场景：超时订单（12306买票支付倒计时）、限时优惠、定时发布

延迟队列 = 死信交换机 + TTL（生存时间）
消息超时未被消费就会变成死信（死信的情况：拒绝被消费、队列满了）


死信交换机：
1.消费者使用basic.reject或basic.ncak声明消费失败，并且消息的requeue属性设置为false
2.消息是一个过期时间，超时无人消费
3.要投递的队列消息堆积满了，最早的消息可能为死信
如果该队列配置了dead-letter-exchange属性，指定了一个交换机，那么队列中的死信就会投递到这个交换机中，而这个交换机为死信交换机（Dead Letter Exchange， DLX）

@Bean
public Queue ttlQueue() {
    return QueueBuilder.durable("simple.queue") // 指定队列名称，并持久化
        .ttl(10000) // 设置队列的超时时间，10秒
        .deadLetterExchange("dl.direct") // 指定死信交换机
        .build();
}

TTL
也就是Time-To-Live。如果一个队列中的消息TTL结束仍未消费，则会变成死信，ttl超时分为两种情况：
    1.消息所在的队列设置了存活时间
    2.消息本身设置了存活时间
    
// 创建消息
Message message = MessageBuilder
    .withBody("Hello， ttl message".getBytes(StandardCharsets.UTF_8))
    .setExpiration("5000")
    .build();
// 消息ID，需要封装到CorrelationData中
CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
// 发送消息
rabbitTemplate.convertAndSend("ttl.direct", "ttl", message, correlationData);


延迟队列插件实现延迟队列DelayExchange
1.声明一个交换机，添加delayed属性为true
2.发送消息时，添加X-Delay头，值为超时时间
~~~

## RabbitMQ消息堆积
~~~
当生产者发送的消息超过了消费者处理的速度，就会导致队列的消息堆积，直到队列存储消息达到上限。之后发送的消息就成为死信，可能会被丢弃，这就是消息堆积问题

解决消息堆积的方法：
1.增加更多消费者，提高消费的速度
2.在消费者内开启线程池（最大利用CPU资源）加快消息处理速度
3.扩大队列容积，提高堆积上限（惰性队列）
    惰性队列;
    1.接收消息后直接存入磁盘而非内存
    2.消费者要消费消息时才会从磁盘中读取并加载到内存
    3.支持数百万条的消息存储
    
    @Bean
    public Queue lazyQueue() {
        return QueueBuilder
            .durable("lazy.queue")
            .lazy()   // 
            .build(); // 指定队列名称，并持久化
        }
    在生命队列的时候可以设置属性x-queue-mode=lazy，表示队列为惰性队列。
    基于磁盘存储，消息上限高
    性能比较稳定，但基于磁盘存储，受限于磁盘IO，时效性会降低
~~~

## RabbitMQ的高可用机制？
~~~
再生产情况下，使用集群来保证高可用
1：普通集群
2：镜像集群
     1.在生产环境下，采用镜像模式搭建集群，总共3个节点
     2.镜像队列结构是一主多从（从就是镜像，所有操作都是主节点完成，然后同步镜像节点
     3.主宕机后，镜像节点会替代新的主（如果在主从同步完成钱，主就已经宕机，可能出现数据丢失）
3：仲裁队列
    与镜像队列一样，都是主从模式，支持主从数据同步，主从同步基于Raft协议，强一致，
    并且使用起来非常简单，不需要额外的配置，在声明队列的时候只要指定这个是仲裁队列即可


    
~~~