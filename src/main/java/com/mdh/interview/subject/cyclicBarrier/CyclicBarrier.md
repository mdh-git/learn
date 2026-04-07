# CyclicBarrier
## 概念
~~~
CyclicBarrier的字面意思是可循环(Cyclic)使用的屏障(Barrier)。
让一组线程到达一个屏障(也可以叫同步点)时被阻塞,直到最后一个线程到达屏障,
屏障才会开门,所有被屏障拦截的线程才会继续干活,线程进入屏障通过CyclicBarrier的await()方法
~~~

## 方法
~~~
线程调用 await() 表示自己已经到达栅栏
BrokenBarrierException 表示栅栏已经被破坏，破坏的原因可能是其中一个线程 await() 时被中断或者超时
~~~

## 核心
~~~
多个线程互相等，都到 “屏障点” 才继续（可循环复用）。

1.CountDownLatch.await()
    底层用 ReentrantLock 保证计数原子性，Condition 管理等待队列；
        每个线程调用 await () 时，先加锁，计数 + 1；
        若未到指定数量（如 5 个线程），线程通过 Condition.await () 挂起；
        最后一个线程调用 await () 时，触发 Condition.signalAll () 唤醒所有线程，同时重置计数（可循环）。
        
        
~~~

## 适用场景
~~~
核心是「多线程协同完成分阶段任务」，必须等所有线程完成当前阶段，才能进入下一个阶段：

1.分阶段任务执行
    比如大数据计算：阶段 1（数据分片读取）→ 阶段 2（分片计算）→ 阶段 3（结果汇总），每个阶段都需所有线程完成后进入下阶段
2.模拟高并发压测
    让所有压测线程同时到达 “发起请求” 节点，模拟真实高并发（秒杀场景）
3.多线程数据处理流水线
    多个线程处理同一批数据，先等所有线程完成数据清洗，再一起进入数据入库阶段
~~~

## await () 阻塞与唤醒逻辑对比
~~~
   维度                          CountDownLatch.await()                           CyclicBarrier.await()
阻塞触发条件                      计数器（state）≠0 时阻塞                             未达到指定等待线程数时阻塞
唤醒触发条件                      计数器归 0，唤醒所有等待线程                     最后一个线程到达屏障点，唤醒所有等待线程
底层实现                              AQS 共享模式                                 ReentrantLock + Condition
复用性                         计数器归 0 后不可重置，一次性使用                   可循环（barrier.reset ()），多次复用
核心逻辑                           等 “外部线程完成任务”                              等 “所有线程到达同一节点” 

~~~

## 总结
~~~
CountDownLatch 是 “单向倒计时等待”，CountDownLatch.await () 等计数器归 0，CountDownLatch 适配「单线程等多线程完成」场景。

CyclicBarrier 是 “多线程互相等，可循环”，CyclicBarrier.await () 等所有线程到屏障点，CyclicBarrier 适配「多线程分阶段同步」场景，
~~~