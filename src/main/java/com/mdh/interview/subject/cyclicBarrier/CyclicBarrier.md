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