# HashMap

##  HashMap和ConcurrentHashMap的重点在于:
~~~
（1）理解HashMap的数据结构的设计和实现思路
（2）在（1）的基础上，理解ConcurrentHashMap的并发安全的设计和实现思路
~~~

## map的区别
~~~
HashMap 不是有序的
TreeMap 和 LinkedHashMap 是有序的
TreeMap 是通过实现 SortMap 接口，能够把它保存的键值对根据 key 排序，基于，从而保证 TreeMap 中所有键值对处于有序状态。
LinkedHashMap 则是通过插入排序（就是你 put 的时候的顺序是什么，取出来的时候就是什么样子）和访问排序（改变排序把访问过的放到底部）让键值有序。
~~~


##  jdk1.7与jdk1.8区别
~~~
HashMap是线程不安全的，其主要体现：
1.在jdk1.7中，在多线程环境下，扩容时会造成环形链或数据丢失。
2.在jdk1.8中，在多线程环境下，会发生数据覆盖的情况。
~~~


## HashMap寻址算法
~~~
put操作
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
    
二次hash：让hash值更加均匀，减少hash冲突
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    
在putVal中(n - 1) & hash直接计算出在数组下标的位置，代替了 % 取模运算，性能更好，但前提是数组必须是2的n次幂
    if ((p = tab[i = (n - 1) & hash]) == null)
~~~

## 为何HashMap的数组长度一定是2的n次幂
~~~
1.计算索引时效率更高：如果是2次幂可以使用位运算代替取模运算
2.扩容时重新计算索引效率更高： hash & oldCap == 0 的元素留在原来的位置，否则 新位置 = 旧位置 + oldCap
~~~


## HashMap在1.7情况下的多线程死循环问题
~~~
jdk7的数据结构是： 数组 + 链表
hashmap中在数组进行扩容的时候，因为链表是头插法，在进行数据迁移的过程中，有可能导致死循环

线程一:读取到当前的hashmap数据，数据中一个链表，在准备扩容时，线程二介入线程二:也读取hashmap，直接进行扩容。
    因为是头插法，链表的顺序会进行颠倒过来。比如原来的顺序是AB，扩容后的顺序是BA，线程二执行结束。
线程一:继续执行的时候就会出现死循环的问题。
    线程一先将A移入新的链表，再将B插入到链头，由于另外一个线程的原因，B的next指向了A，所以B->A->B,形成循环。

当然，JDK8将扩容算法做了调整，不再将元素加入链表头(而是保持与扩容前一样的顺序)，尾插法，就避免了jdk7中死循环的问题。
~~~