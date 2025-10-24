# Java集合框架体系


# 集合框架
~~~
一、Collection单列集合
    1. List 有序、可重复
        1.1 vector 数组结构、线程安全（synchronized 加锁）
        1.2 ArrayList 数组结构、线程不安全
        1.3 LinkedList 链表结构、线程不安全（双向链表）
    2. Set 无序、不可重复
        2.1 HashSet 哈希表结构、线程不安全
                LinkedHashSet 哈希表+链表结构、线程不安全
        2.2 TreeSet 红黑树结构、线程不安全

二、Map双列集合
    1.HashTable 哈希表结构，线程安全（synchronized 加锁） 不推荐使用
        Properties 
    2.HashMap 哈希表结构，线程不安全
        LinkedHashMap 哈希表+链表结构，线程不安全
    3.ConcurrentHashMap 哈希表结构，线程安全
    4.TreeMap 红黑树结构，线程不安全
~~~
