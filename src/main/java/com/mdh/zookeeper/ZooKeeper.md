# ZooKeeper

## ZAB协议
~~~
ZAB协议是ZooKeeper用来实现一致性的原子广播协议，该协议描述了ZooKeeper是如何实现一致性的，分为三个阶段：
1.领导者选举阶段：从ZooKeeper集群中选出一个节点作为leader，所有的写请求都会由leader节点来处理
2.数据同步阶段：集群中所有节点中的数据要和leader节点保持一致，如果不一致则要进行同步
3.请求广播阶段：当leader节点接收到写请求时，会利用两阶段提交广播该写请求，使得写请求像事务一样在其他节点上执行，达到节点上的数据实时一致

ZooKeeper只是尽量达到强一致性，实际上仍然是最终一致性的。
~~~

## 为什么ZooKeeper可以用来作为注册中心？
~~~
可以利用ZooKeeper的临时节点和watch机制来实现注册中心的自动注册和发现，另外ZooKeeper中的数据都是存在内存中的，并且ZooKeeper底层采用了
nio，多线程模型，所以ZooKeeper的性能也是比较高的，所以可以用来作为注册中心，但是考虑到注册中心应该是注册可用性的话，那么ZooKeeper则不太合适，
因为ZooKeeper是CP的，它注重的是一致性，所以集群数据不一致时，集群将不可用，所以Redis、Eureka、Nacos来作为注册中心将更合适。
~~~


## ZooKeeper中的领导者选举的流程是怎么样的？
~~~
对于ZooKeeper集群，整个集群需要从集群节点中选出一个节点作为leader，大体流程如下：
1.集群中各个节点首先都是观望状态（Looking），一开始都会投票给自己，认为自己比较适合作为leader
2.然后相互交互投票，每个节点会收集其他节点发过来的选票，然后pk，先比较zxid(日志的新旧程度)，zxid大着获胜，如果zxid相等则比较myid，myid大着获胜
3.一个节点收到其它节点发过来的选票，经过PK后，如果PK输了，则改票，此节点就会投给zxid或者myid更大的节点，并将选票放入自己的投票箱中，并将洗的呢选票发送给其它节点
4.如果pk是平局则将接收到的选票放入自己的投票箱中
5.如果pk赢了，则忽略所接收到的选票
6.当然一个节点将一张选票放入自己的投票箱之后，就会从投票箱中统计票数，看是否超过一半的节点都和自己所投的节点是一样的，如果超过半数，那么则认为当前自己所投的节点是leader
7.集群中每个节点都会经过同样的流程，pk的规则也是一样的，一旦改票就会告诉其他服务器，所以最终各个节点中的投票箱中选票也将是一样的，所以各个节点最终选出来的leader也是一样的，这样集群的leader就选举好了
~~~

## ZooKeeper集群中节点之间数据是如何同步的？
~~~
1.首先集群启动时，会选进行领导者选举，确定那个节点是leader，那些节点是follower和observer
2.然后leader会和其他节点进行数据同步，采用发送快照和发送Diff日志的方式（新的节点加入时）
3.集群在工作过程中，所有的写请求都会交给leader节点来进行处理，从节点只能处理读请求
4.leader节点收到一个写请求时，会通过两阶段机制来处理（已存在节点数据同步）
5.leader节点会将该写请求对应的日志发送给其他follower节点，并等待follower节点持久化日志成功
6.follower节点接收到日志后会进行持久化，如果持久化成功则发送一个ack给leader节点
7.当leader节点接收到半数以上的ack后，就会开始提交，先更新leader节点本地的内存数据
8.然后发送commit命令给follower节点，follower节点接收到commit命令后会更新各自本地内存数据
9.同时leader节点还是将当前写请求直接发送给observer节点，observer节点收到leader发过来的写请求后直接更新本地内存数据
10.最后leader节点返回客户端写请求响应成功
11.通过同步机制和两阶段提交机制来达到集群中节点数据一致性
~~~
