# Semaphore

## 说明
~~~
Semaphore(默认为非公平锁) 是一个计数信号量，必须由获取它的线程释放。

常用于限制可以访问某些资源的线程数量，例如通过 Semaphore 限流。

Semaphore 只有3个操作：

1.初始化
2.增加
3.减少

Semaphore 是 synchronized 的加强版，作用是控制线程的并发数量。
单纯的synchronized 关键字是实现不了的。
~~~

## Semaphore 
~~~
核心：许可证机制
Semaphore 基于 AQS 共享模式 实现，核心是「一组许可证（permits）」：
1.初始化时指定许可证数量（如 new Semaphore(5)），底层赋值给 AQS 的 state；
2.线程调用 acquire() 获取许可证，成功则 state-1，失败则阻塞等待；
3.线程调用 release() 归还许可证，state+1，唤醒等待队列中的线程；
4.核心规则：只有拿到许可证的线程，才能访问受限资源，实现并发数控制。


1. acquire ()（获取许可证）
    底层调用 AQS 的 acquireSharedInterruptibly(int arg)
        尝试 CAS 减少 state（许可证数），剩余数≥0 则获取成功；
        剩余数 < 0 则封装成 Node 加入 AQS 共享队列，调用 park() 挂起，等待被唤醒。
        
2.release ()（归还许可证）
    底层调用 AQS 的 releaseShared(int arg)
        循环 CAS 增加 state（归还许可证），保证原子性；
        归还成功后，调用 doReleaseShared() 唤醒队列中等待获取许可证的线程。
    
~~~

## 核心适用场景
~~~
控制并发数（限流）

1.接口限流
    限制接口同时处理的请求数（如秒杀接口限 100 并发）
2.资源池访问控制
    数据库连接池 / 线程池，限制同时获取连接的线程数
3.硬件资源保护
    限制同时访问 GPU / 打印机等硬件的线程数
~~~

## 总结
~~~
1.Semaphore 基于 AQS 共享模式，用 state 存储许可证数，acquire () 拿许可证、release () 还许可证；
2.acquire () 核心是 “CAS 减 state，不足则入队阻塞”，release () 核心是 “CAS 加 state，成功则唤醒等待线程”；
3.核心场景是控制并发访问数，如接口限流、资源池访问控制，是高并发限流的核心工具。
~~~


## 如何控制某个方法并发访问线程的数量
~~~
在多线程中提供了一个工具类Semaphore，信号量。在并发的情况下，可以控制方法的访问量

1.创建Semaphore对象，设置一个容量
2.acquire()可以请求一个信号量，这时候的信号量个数-1
3.release()释放一个信号量，此时信号量个数+1
~~~