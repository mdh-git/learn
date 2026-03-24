# ConcurrentHashMap 在JDK 1.7 和 1.8 的区别

~~~
1.底层数据结构:
    JDK1.7底层采用分段的数组+链表实现
    JDK1.8采用的数据结构跟HashMap1.8的结构一样，数组+链表/红黑二叉树
2.加锁的方式
    JDK1.7采用Segment分段锁，底层使用的是ReentrantLock
    JDK1.8采用CAS添加新节点，采用synchronized锁定链表或红黑二叉树的首节点，相对Segment分段锁粒度更细，性能更好
~~~