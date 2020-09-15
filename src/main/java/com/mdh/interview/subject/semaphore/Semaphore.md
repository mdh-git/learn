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