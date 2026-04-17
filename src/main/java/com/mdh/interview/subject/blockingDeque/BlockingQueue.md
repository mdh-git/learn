# BlockingQueue

## 解释
~~~
https://www.cnblogs.com/lenmom/p/12018370.html

https://www.jianshu.com/p/7b2f1fa616c6

阻塞队列（BlockingQueue）是一个支持两个附加操作的队列。
这两个附加的操作是：在队列为空时，获取元素的线程会等待队列变为非空。
                  当队列满时，存储元素的线程会等待队列可用。

阻塞队列常用于生产者和消费者的场景，生产者是往队列里添加元素的线程，消费者是从队列里拿元素的线程。
阻塞队列就是生产者存放元素的容器，而消费者也只从容器里拿元素。
~~~

## 线程池中的阻塞队列
~~~

ArrayBlockingQueue: 数组实现有界队列
    一个基于数组实现的有界阻塞队列,此队列按 FIFO(先进先出)原则对元素进行排序。

LinkedBlockingQueue: 链表结构无界队列
    一个基于链表结构的阻塞队列,此队列按FIFO(先进先出)排序元素,吞吐量通常高于ArrayBlockingQueue。
    LinkedBlockingQueue是允许两个线程同时在两端进行入队或出队的操作的，但一端同时只能有一个线程进行操作，这是通过两把锁来区分的；
    为了维持底部数据的统一，引入了AtomicInteger的一个count变量，表示队列中元素的个数。
    count只能在两个地方变化，一个是入队的方法（可以+1），另一个是出队的方法（可以-1），而AtomicInteger是原子安全的，所以也就确保了底层队列的数据同步。 

DelayQueue： 延迟任务队列
    是一个任务定时周期的延迟执行的队列。 根据指定的执行时间从小到大排序，否则根据插入到队列的先后排序。
    
PriorityBlockingQueue： 有优先级无界阻塞队列
    是具有优先级的无界阻塞队列。与无界队列类似，优先级队列可以保证所有任务都会被执行，但不同的是优先级队列可以对任务进行管理和排序，确保高优先级的任务先优先执行。

SynchronousQueue: 无元素存储阻塞队列
    一个不存储元素的阻塞队列,每个插入操作必须等到另外一个线程调用移除操作,否则插入操作一直处于阻塞状态,吞吐量通常高于LinkedBlockingQueue。
    
~~~


## ArrayBlockingQueue
~~~
ArrayBlockingQueue：数组实现 + 全局单锁，有界、读写互斥；
基于数组实现的有界阻塞队列，核心靠「循环数组 + 头尾指针」实现空间复用：

初始化时指定固定容量（如new ArrayBlockingQueue(10)），数组大小不可变；
用takeIndex（出队指针）、putIndex（入队指针）标记数组读写位置；
当putIndex/takeIndex到达数组末尾时，重置为 0，形成 “循环”；
用count记录队列元素数量，判断队列空 / 满。

数组：[A, B, C, _, _] → takeIndex=0, putIndex=3, count=3
出队A → takeIndex=1, count=2
入队D → putIndex=4, count=3
入队E → putIndex=0（循环）, count=4

入队（put）与出队（take）的锁逻辑：全局独占锁
ArrayBlockingQueue 用单个 ReentrantLock 全局锁+ 两个 Condition 实现阻塞，
核心特点：「读写互斥，同一时间只能读 / 写其一」：
    1. 入队（put (E e)）—— 队列满则阻塞
        获取全局锁 → 检查队列满 → 满则通过notFull.await()挂起（释放锁）→ 被唤醒后入队 → 唤醒出队等待线程 → 释放锁。
    2. 出队（take ()）—— 队列空则阻塞
        获取全局锁 → 检查队列空 → 空则通过notEmpty.await()挂起（释放锁）→ 被唤醒后出队 → 唤醒入队等待线程 → 释放锁。
        
全局锁：读写共用一把锁，高并发下读写互相阻塞，性能略低；
Condition 分工：notEmpty管出队等待，notFull管入队等待，精准唤醒。

入队 / 出队都要获取同一把全局锁，入队时出队必须等，出队时入队必须等；
即使队列既不满也不空，读写也无法并行，高并发下性能瓶颈明显。
~~~

## ArrayBlockingQueue原理
~~~
它是一个基于数组实现的、固定容量的、线程安全的队列，遵循先进先出（FIFO）的原则。

1.有界性（Bounded）
    在创建时必须指定容量（capacity）。
    一旦创建，容量不可动态扩容。
    当队列满时，生产者线程会被阻塞；当队列空时，消费者线程会被阻塞。
2.基于数组（Array-based）
    底层使用一个 Object[] 数组存储元素。
    相比链表（如 LinkedBlockingQueue），数组在内存中更紧凑，缓存命中率更高，但容量受限。
3.公平性策略（Fairness）
    构造时可以指定是否开启公平锁。
    非公平（默认）：吞吐量更高，但可能导致某些线程长时间等待（“饿死”）。
    公平：按照等待时间的先后顺序获取锁，减少线程饥饿，但吞吐量会略微下降。
    
底层原理
1.独占锁 (ReentrantLock)
    为了保证多线程环境下的数据安全，所有的入队和出队操作都必须先获取同一把锁（lock）。这意味着同一时刻，只能有一个线程在修改队列状态（无论是生产还是消费）。
2. 双条件变量 (Condition)
    为了实现“阻塞”功能，它使用了两个条件变量来控制线程的等待和唤醒：
    notEmpty：当队列空时，消费者线程调用 take() 会在此条件上等待；当生产者放入元素后，会唤醒该条件。
    notFull：当队列满时，生产者线程调用 put() 会在此条件上等待；当消费者取出元素后，会唤醒该条件。
3. 环形数组（Circular Array）
    虽然底层是数组，但它逻辑上是一个环。
    使用 putIndex 指向下一个插入位置，takeIndex 指向下一个取出位置。
    当指针到达数组末尾时，会自动回到数组头部（下标 0），从而实现数组空间的循环利用。
~~~

## LinkedBlockingQueue
~~~
LinkedBlockingQueue：链表实现 + 双锁分离，默认无界（可指定容量）、读写并行。

LinkedBlockingQueue 把入队和出队的锁彻底分离，实现读写并行：

（1）入队锁（putLock）—— 仅管控入队操作，尾结点入队
（2）出队锁（takeLock）—— 仅管控出队操作，头结点出队

双锁优势：入队线程只竞争 putLock，出队线程只竞争 takeLock，读写操作完全并行，高并发下性能远高于 ArrayBlockingQueue。


核心是「高并发、大容量 / 无界」的生产者 - 消费者场景：
1.高并发生产消费
    如电商订单处理：生产者（下单线程）和消费者（处理线程）并发量高，需要读写并行提升性能
2.大容量 / 无界缓存
    如日志收集：日志产生速度不确定，需要无界队列缓存，避免丢失（需注意内存溢出风险）
3.动态扩容场景
    数据量波动大，链表节点按需创建，比固定数组更节省内存（低负载时）

~~~


## ArrayBlockingQueue  VS   LinkedBlockingQueue
~~~
维度                      ArrayBlockingQueue                          LinkedBlockingQueue
锁设计                 全局独占锁（1 个 ReentrantLock）                双锁分离（putLock+takeLock）
读写关系                读写互斥，同一时间只能读 / 写其一                  读写并行，入队 / 出队互不阻塞
Condition 数量       2 个（notEmpty/notFull），绑定全局锁      2 个（notEmpty 绑定 takeLock，notFull 绑定 putLock）
容量特性                强制有界（初始化必须指定容量）                 默认无界（Integer.MAX_VALUE），可指定有界
结构实现                    循环数组，无扩容开销                          单向链表，动态扩容（节点按需创建）
~~~


##  方法
~~~
阻塞队列提供了四种处理方法:

方法\处理方式  抛出异常      返回特殊值       一直阻塞      超时退出
插入方法      add(e)        offer(e)         put(e)    offer(e,time,unit)
移除方法     remove()       poll()           take()    poll(time,unit)
检查方法    element()       peek()           不可用     不可用
~~~

## SynchronousQueue
~~~
SynchronousQueue没有容量

SynchronousQueue是一个不存储元素的阻塞队列。
每一个put操作必须等待一个take操作,否者不能继续添加元素,反之亦然

~~~
