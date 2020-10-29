# AQS
~~~
https://blog.csdn.net/TZ845195485/article/details/109210263
~~~

## 介绍
~~~

https://juejin.im/post/6844904146127044622

AbstractQueuedSynchronizer
AQS是一个用来构建锁和同步器的框架,使用AQS能简单且高效地构造出应用广泛的大量的同步器
比如：ReentrantLock Semaphore

是用来构建锁或者其它同步器组件的重量级基础框架以及整个JUC体系的基石,通过内置的FIFO队列来完成
资源获取线程的排队工作,并通过一个int类型变量表示持有锁的状态。
~~~

## 原理
~~~
AQS核心思想是,如果被请求的共享资源空闲,则将当前请求资源的线程设置为有效的工作线程,并且将共享资源设置为锁定状态。
如果被请求的共享资源被占用,那么久需要一套线程阻塞等待以及被唤醒时锁分配的机制,
这个机制AQS是用CLH队列锁实现的,即将暂时获取不到锁的线程加入到队列中。

AQS使用一个volatile的int类型的成员变量来完成同步状态,通过内置的FIFO队列来完成资源获取的排队工作
将每条要去抢占资源的线程封装成一个Node节点来实现锁的分配,通过CAS完成对State值的修改。


CLH(Craig,Landin,and Hagersten)队列是一个虚拟的双向队列(虚拟的双向队列即不存在队列实例,仅存在节点之间的关联关系)
AQS是将每条请求共享资源的线程封装成一个CLH锁队列的一个结点(Node)来实现锁的分配。
~~~

## AQS 定义两种资源共享方式
~~~
1)Exclusive（独占）
    ReentrantLock
2)Share（共享）
    Semaphore/CountDownLatch
~~~

## AQS 底层使用了模板方法模式
~~~
同步器的设计是基于模板方法模式的，如果需要自定义同步器一般的方式是这样（模板方法模式很经典的一个应用）：

（1）使用者继承 AbstractQueuedSynchronizer 并重写指定的方法。（这些重写方法很简单，无非是对于共享资源 state 的获取和释放）
（2）将 AQS 组合在自定义同步组件的实现中，并调用其模板方法，而这些模板方法会调用使用者重写的方法。


AQS 使用了模板方法模式，自定义同步器时需要重写下面几个 AQS 提供的模板方法：

isHeldExclusively()//该线程是否正在独占资源。只有用到condition才需要去实现它。
tryAcquire(int)//独占方式。尝试获取资源，成功则返回true，失败则返回false。
tryRelease(int)//独占方式。尝试释放资源，成功则返回true，失败则返回false。
tryAcquireShared(int)//共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
tryReleaseShared(int)//共享方式。尝试释放资源，成功则返回true，失败则返回false。
~~~
