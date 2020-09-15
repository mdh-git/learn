# BlockingDeque
## 解释
~~~
阻塞队列

ArrayBlockingQueue: 一个基于数组结构的有限阻塞队列,此队列按 FIFO(先进先出)原则对元素进行排序。

LinkedBlockingQueue: 一个基于链表结构的阻塞队列,次队列按FIFO(先进先出)排序元素,吞吐量通常高于ArrayBlockingQueue。
    
SynchronousQueue: 一个不存储元素的阻塞队列,每个插入操作必须等到另外一个线程调用移除操作,否则插入操作一直处于阻塞状态,吞吐量通常高于LinkedBlockingQueue。

~~~

## 