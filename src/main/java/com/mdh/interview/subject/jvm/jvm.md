# JVM

## JVM内存结构
~~~

Class files    ->  类装载器子系统  Class loader
                             |
                             |
              运行时数据区(Runtime Data Area)

  线程共有                                         线程私有

方法区(Method Area)                 java栈(java stack)         本地方法栈(Native Method stack)

堆(Heap)                             程序计数器(Program Counter Register)


java8 之后把永久代改成了元空间
~~~

## Class loader
~~~
四种类加载器:
双亲委派机制:
java类加载沙箱安全机制
~~~

## 常见的垃圾回收算法
~~~
1. 引用计数 (JVM的实现一般不采用这种方式)
    缺点: (1) 每次对对象赋值均要维护引用计数器,且计数器本身也有一定的消耗;
          (2) 较难处理循环引用

2. 复制
    优点: 没有产生内存碎片 
    缺点: 浪费空间,大对象复制耗时
                    堆(Heap)
    
        Young                              Old
    Eden(8):From(1):To(1)                 
    新生代(1/3)堆空间                      老年代(2/3)堆空间

    MinorGC的过程(复制->清空->互换)
1. eden、SurvivorFrom复制到SurvivorTo,年龄+1
首先,当Eden区满的时候回触发第一次GC,把还活着的对象拷贝到SurvivorFrom区,当Eden区再次复发GC的时候会扫描
Eden区和From区域,对这两个进行垃圾回收,经过这次回收后还存活的对象,则直接复制到To区域(如果有对象的年龄已经
到达了老年的标准,则复制到老年代区),同时把这些对象的年龄+1

2.清空Eden、SurvivorFrom
然后,清空Eden和SurvivorFrom中的对象,也即复制之后有交换,谁空谁是to

3.SurvivorTo和SurvivorFrom互换
最后,SurvivorTo和SurvivorFrom互换,原SurvivorTo成为下一次GC的SurvivorFrom区。部分对象会在From和To区域中
复制来复制去,如此交换15次(由JVM参数MaxTenuringThreshold决定,这个参数默认值是15),最终如果还是存活,就存入到老年代


3. 标记清除(一般用于老年代)
    优点: 
    缺点: 产生内存碎片 

    标记清除算法(Mark-Sweep)
    算法分成标记和清除两个阶段,先标记出要回收的对象,然后统一回收这些对象。

4. 标记整理(标记压缩 Mark-Compact)(一般用于老年代)
    优点: 没用内存碎片,可以利用bump-the-pointer
    缺点: 需要移动对象的成本,耗时高

    原理:
        1.标记(Mark):
            与标记-清除 一样
        2.压缩(Compact)
            再次扫描,并往一端滑动存活对象

~~~