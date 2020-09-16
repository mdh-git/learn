# BlockingQueue

## 解释
~~~
https://www.cnblogs.com/lenmom/p/12018370.html

阻塞队列（BlockingQueue）是一个支持两个附加操作的队列。
这两个附加的操作是：在队列为空时，获取元素的线程会等待队列变为非空。
                  当队列满时，存储元素的线程会等待队列可用。

阻塞队列常用于生产者和消费者的场景，生产者是往队列里添加元素的线程，消费者是从队列里拿元素的线程。
阻塞队列就是生产者存放元素的容器，而消费者也只从容器里拿元素。
~~~

~~~
阻塞队列

ArrayBlockingQueue: 一个基于数组结构的有限阻塞队列,此队列按 FIFO(先进先出)原则对元素进行排序。

LinkedBlockingQueue: 一个基于链表结构的阻塞队列,次队列按FIFO(先进先出)排序元素,吞吐量通常高于ArrayBlockingQueue。
    
SynchronousQueue: 一个不存储元素的阻塞队列,每个插入操作必须等到另外一个线程调用移除操作,否则插入操作一直处于阻塞状态,吞吐量通常高于LinkedBlockingQueue。

SynchronousQueue: 一个不存储元素的阻塞队列，单个元素队列
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