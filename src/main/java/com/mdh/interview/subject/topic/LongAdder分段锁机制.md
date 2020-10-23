# LongAdder分段锁机制
~~~
https://blog.csdn.net/rammus7/article/details/106603840
https://blog.csdn.net/eluanshi12/article/details/84871879
https://blog.csdn.net/codingtu/article/details/89047291
~~~

~~~
内部实现了自动分段迁移的机制，也就是如果某个Cell的value执行CAS失败了，那么就会自动去找另外一个Cell分段内的value值进行CAS操作。
~~~

~~~
Java 8有一个新的类，java.util.concurrent.atomic.LongAdder。
尝试使用分段CAS以及自动分段迁移的方式来大幅度提升多线程高并发执行CAS操作的性能

 线程1           线程2-------------  线程3
 | |               |             |   |   |
 | |               |             |   |   |
 | |               |             |   |   |
 | --------------  |   ---------------   |
 |               | |   |         |       |
 |               | |   |         |       |
 |              base = 5         |       |
 |                               |       |
 cell              cell ----------      cell
value=2           value=3              value=6


在LongAdder的底层实现中，首先有一个base值，刚开始多线程来不停的累加数值，都是对base进行累加的，比如刚开始累加成了base = 5。
接着如果发现并发更新的线程数量过多，就会开始施行分段CAS的机制，也就是内部会搞一个Cell数组，每个数组是一个数值分段。
这时，让大量的线程分别去对不同Cell内部的value值进行CAS累加操作，这样就把CAS计算压力分散到了不同的Cell分段数值中了！
这样就可以大幅度的降低多线程并发更新同一个数值时出现的无限循环的问题，大幅度提升了多线程并发更新数值的性能和效率！
而且他内部实现了自动分段迁移的机制，也就是如果某个Cell的value执行CAS失败了，那么就会自动去找另外一个Cell分段内的value值进行CAS操作。
这样也解决了线程空旋转、自旋不停等待执行CAS操作的问题，让一个线程过来执行CAS时可以尽快的完成这个操作。
最后，如果你要从LongAdder中获取当前累加的总值，就会把base值和所有Cell分段数值加起来。
~~~

## AtomicLong 与 LongAdder
~~~
AtomicLong是多个线程针对单个热点值value进行原子操作。
LongAdder是每个线程拥有自己的槽,各个线程一般只对自己槽中的那个值进行CAS操作。

比如有三个线程ThreadA、ThreadB、ThreadC,每个线程对value增加10
对于AtomicLong,最终结果的计算始终是下面这个形式：
     value = 10 + 10 + 10 = 30
但是对于LongAdder来说,内部又一个base变量,一个Cell[]数组。
base变量：非竞态条件下,直接累加到该变量上
Cell[]数组：竞态条件下,累加各个线程自己的槽Cell[i]中
最终结果的计算是下面这个形式：
  value = base + （Cell[0] + ... + Cell[i]）
https://blog.csdn.net/reed1991/article/details/106598355
~~~