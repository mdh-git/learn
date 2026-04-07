# CountDownLatch

## 概念
~~~
CountDownLatch是一个同步工具类，用来协调多个线程之间的同步，或者说起到线程之间的通信（而不是用作互斥的作用）。

CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。
使用一个计数器进行实现。
计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。
当计数器的值为0时，表示所有的线程都已经完成一些任务，然后在CountDownLatch上等待的线程就可以恢复执行接下来的任务。
~~~

## 用法
~~~
1、某一线程在开始运行前等待n个线程执行完毕。
将CountDownLatch的计数器初始化为new CountDownLatch(n)，每当一个任务线程执行完毕，就将计数器减1 countdownLatch.countDown()，
当计数器的值变为0时，在CountDownLatch上await()的线程就会被唤醒。
一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。


2、实现多个线程开始执行任务的最大并行性。
注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。
类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。
做法是初始化一个共享的CountDownLatch(1)，将其计算器初始化为1，多个线程在开始执行任务前首先countdownlatch.await()，
当主线程调用countDown()时，计数器变为0，多个线程同时被唤醒。
~~~

## 不足
~~~
CountDownLatch是一次性的，计算器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当CountDownLatch使用完毕后，它不能再次被使用。
~~~

## CountDownLatch和CyclicBarrier区别
~~~
1.countDownLatch是一个计数器，线程完成一个记录一个，计数器递减，只能只用一次
2.CyclicBarrier的计数器更像一个阀门，需要所有线程都到达，然后继续执行，计数器递增，提供reset功能，可以多次使用
~~~

## 核心
~~~
CountDownLatch 基于 AQS共享模式实现，核心是「一个计数器 + 等待队列」
    初始化时指定计数器值（如new CountDownLatch(3)），底层赋值给 AQS 的state；
    调用await()的线程会阻塞，直到计数器（state）归 0；
    其他线程调用countDown()让计数器 - 1，直到 state=0 时，唤醒所有等待线程。
    
1. await ()（等待计数归零）
    底层调用 AQS 的acquireSharedInterruptibly(int arg)
        检查 state 是否为 0，是则直接返回；
        否则封装成 Node 加入 AQS 共享队列，调用LockSupport.park()挂起线程。
        
2. countDown ()（计数 - 1）
    底层调用 AQS 的releaseShared(int arg)
        循环 CAS 将 state-1，保证原子性；
        只有 state 减到 0 时，才触发doReleaseShared()唤醒所有等待线程。
~~~

## 使用场景
~~~
1.任务拆分并行执行
    主线程拆分 N 个子任务，等所有子任务完成后汇总结果
2.服务启动检查
    应用启动时，等所有依赖组件（数据库、缓存）初始化完成
3.批量接口测试
    等所有测试线程执行完，统计成功率 / 响应时间
~~~

## 总结
~~~
1.CountDownLatch 基于 AQS 共享模式，用 state 存储计数器，await () 等 state=0，countDown () 让 state-1；
2.await () 核心是 “state≠0 则入队挂起”，countDown () 核心是 “CAS 减 state，归 0 则唤醒所有等待线程”；

适用「等待多线程完成任务」场景，计数器只能用一次（state 归 0 后无法重置）。
~~~