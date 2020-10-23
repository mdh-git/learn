# volatile底层的内存屏障是如何实现的
~~~
https://blog.csdn.net/huyongl1989/article/details/90712393
~~~

~~~
内存屏障其实也是一种JVM指令，Java内存模型的重排规则会要求Java编译器在生成JVM指令时插入特定的内存屏障指令，通过这些内存屏障指令来禁止特定的指令重排序。

另外内存屏障还具有一定的语义：
内存屏障之前的所有写操作都要回写到主内存，
内存屏障之后的所有读操作都能获得内存屏障之前的所有写操作的最新结果(实现了可见性)。
因此重排序时，不允许把内存屏障之后的指令重排序到内存屏障之前。

JVM中提供了四类内存屏障指令：

屏障类型	        指令示例	                            说明
LoadLoad	Load1; LoadLoad; Load2	      保证load1的读取操作在load2及后续读取操作之前执行
StoreStore	Store1; StoreStore; Store2	  在store2及其后的写操作执行前，保证store1的写操作已刷新到主内存
LoadStore	Load1; LoadStore; Store2	  在stroe2及其后的写操作执行前，保证load1的读操作已读取结束
StoreLoad	Store1; StoreLoad; Load2	  保证store1的写操作已刷新到主内存之后，load2及其后的读操作才能执行
~~~