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
    一个基于数组结构的有限阻塞队列,此队列按 FIFO(先进先出)原则对元素进行排序。

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

##  方法
阻塞队列提供了四种处理方法:

方法\处理方式  抛出异常      返回特殊值       一直阻塞      超时退出
插入方法      add(e)        offer(e)         put(e)    offer(e,time,unit)
移除方法     remove()       poll()           take()    poll(time,unit)
检查方法    element()       peek()           不可用     不可用

## SynchronousQueue
~~~
SynchronousQueue没有容量

SynchronousQueue是一个不存储元素的阻塞队列。
每一个put操作必须等待一个take操作,否者不能继续添加元素,反之亦然

~~~
