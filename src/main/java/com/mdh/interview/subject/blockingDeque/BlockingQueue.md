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

~~~
阻塞队列

ArrayBlockingQueue: 一个基于数组结构的有限阻塞队列,此队列按 FIFO(先进先出)原则对元素进行排序。

LinkedBlockingQueue: 一个基于链表结构的阻塞队列,次队列按FIFO(先进先出)排序元素,吞吐量通常高于ArrayBlockingQueue。
    LinkedBlockingQueue是允许两个线程同时在两端进行入队或出队的操作的，但一端同时只能有一个线程进行操作，这是通过两把锁来区分的；
    为了维持底部数据的统一，引入了AtomicInteger的一个count变量，表示队列中元素的个数。count只能在两个地方变化，一个是入队的方法（可以+1），另一个是出队的方法（可以-1），而AtomicInteger是原子安全的，所以也就确保了底层队列的数据同步。 

    
SynchronousQueue: 一个不存储元素的阻塞队列,每个插入操作必须等到另外一个线程调用移除操作,否则插入操作一直处于阻塞状态,吞吐量通常高于LinkedBlockingQueue。
    
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

# synchronized 和 Lock的区别? 用心的lock有什么好处？
~~~
1. 原始构造
    synchronized是关键字属于JVM层面
        MONITORENTER(monitorenter  通过查看字节码文件) 底层是通过monitor对象来完成,其实wait/notify等方法也依赖于monitor对象,只有在同步块或方法中才能调wait/notify等方法
        monitorexit
        (字节码文件 一个monitorenter  两个monitorexit   synchronized通过一次正常退出,一次异常退出,保证不会出现死锁 )

    Lock是具体类(java.util.concurrent.locks.Lock)是api层面的锁
 
2.使用方法
    synchronized不需要用户去手动释放锁,当synchronized代码执行完成后系统会自动让线程释放对锁的占用

    ReentrantLock则需要用户去手动释放锁,没有主动释放锁,就有可能导致出现死锁现象。
        需要lock()和unlock()方法配合try/finally语句块完成

3.等待是否可中断
    synchronized不能中断,除非抛出异常或者正常进行完成

    ReentrantLock 可中断   1.设置超时方法 tryLock(long time, TimeUnit unit)
                          2. lockInterruptibly放代码块中,调用interrupt()方法可中断

public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

4.加锁是否公平
    synchronized非公平锁

    ReentrantLock两者都可以,默认采用非公平锁, 构造方法可以传入boolean值,true为公平锁

public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

public ReentrantLock() {
        sync = new NonfairSync();
    }

5.锁绑定多个条件Condition
    synchronized没有

    ReentrantLock用来实现分组唤醒需要唤醒的线程们,可以精确唤醒,而不是像synchronized要么随机唤醒一个要么唤醒全部线程。
~~~