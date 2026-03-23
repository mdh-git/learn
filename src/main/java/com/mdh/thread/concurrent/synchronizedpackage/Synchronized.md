# Sync

## 偏向锁
~~~
轻量级锁在没有竞争时(就自己这个线程)，每次重入仍然需要执行CAS操作。
Java 6中引入了偏向锁来做进一步优化:只有第一次使用CAS将线程ID设置到对象的Mark Word头，之后发现这个线程ID是自己的就表示没有竞争，不用重新CAS。
以后只要不发生竞争，这个对象就归该线程所有

~~~

## 轻量级锁
~~~
加锁流程
    1.在线程栈中创建一个Lock Record，将其obj字段指向锁对象。
    2.通过CAS指令将Lock Record的地址存储在对象头的mark word中，如果对象处于无锁状态则修改成功，代表该线程获得了轻量级锁。
    3.如果是当前线程已经持有该锁了，代表这是一次锁重入。设置Lock Record第一部分为null，起到了一个重入计数器的作用。
    4.如果CAS修改失败，说明发生了竞争，需要膨胀为重量级锁。

解锁过程
    1.遍历线程栈,找到所有obj字段等于当前锁对象的Lock Record。
    2.如果Lock Record的Mark Word为null,代表这是一次重入，将obj设置为null后continue。
    3.如果Lock Record的Mark Word不为null,则利用CAS指令将对象头的mark word恢复成为无锁状态。如果失败则膨胀为重量级锁。
~~~