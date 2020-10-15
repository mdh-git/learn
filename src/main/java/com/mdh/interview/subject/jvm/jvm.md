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

## JVM的参数类型
~~~
1.标配参数
    -version
    -help
    java -showversion
2.X参数
    -Xint(解释执行)
    -Xcomp(第一次使用就编译成本地代码)
    -Xmixed(混合模式)
3.XX参数
    (1)Boolean类型
        公式
            -XX:+或者- 某个属性值(+ 表示开启  -表示关闭)
        case
            是否打印GC收集细节
                -XX:-PrintGCDetails
                -XX:+PrintGCDetails
            是否使用串行垃圾回收器
                -XX:-UseSerialGC
                -XX:+UseSerialGC
    (2)KV设置类型
        公式
            -XX:属性key=属性值value
        case
            -XX:MetaspaceSize=21807104
            -XX:MaxTenuringThreshold=15(新生代存活15次)
    jinfo类型,如何查看当前运行程序的额配置
        -jinfo flags 进程号(查看所有的)
    
    -Xms等价于-XX:InitialHeapSize=134217728(初始堆内存)
    -Xmx等价于-XX:MaxHeapSize=2134900736(最大堆内存)
~~~

## 默认值
~~~
java -XX:+PrintFlagsInitial
    查看JVM的初始值 =

java -XX:+PrintFlagsFinal
    查看修改的参数  := 

java -XX:+PrintCommanddLineFlags -vrsion
    最后一个参数表示使用的是哪个垃圾回收器

~~~

## JVM常用基本配置参数
~~~
-Xms(初始内存大小)  Runtime.getRuntime().totalMemory();  (默认物理内存的1/64)
-Xmx(最大内存大小)  Runtime.getRuntime().maxMemory();  (默认物理内存的1/4)
-Xss(设置单个线程栈的大小,一般默认为512k~1024k)  等价于 -XX:ThreadStackSize (如果-XX:ThreadStackSize=0  代表使用JVM默认的大小)
-Xmn(设置年轻代大小)
-XX:MetaspaceSize(设置元空间大小)
    元空间的本质好永久代类似,都是对JVM规范中方法区的实现。
    不过元空间与永久代之间最大的区别:
    元空间并不在虚拟机中,而是使用本地内存。
    因此,默认情况下,元空间的大小仅受本地内存限制。
         -XX:MetaspaceSize = 1024m -XX:+PrrintFlagsFinal
-XX:+PrintGCDetails(打印出GC的详细过程)
-XX:SurvivorRatio(设置新生代中Eden和S0/S1空间的比例) 默认-XX:SurvivorRatio=8,Eden:S0:S1=8:1:1
-XX:NewRatio(设置年轻代与老年代在堆结构到的占比) 默认 -XX:NewRatio=2 新生代:老年代=2:1
-XX:MaxTenuringThreshold(设置垃圾的最大年龄) 默认15次(会动态调整)

配置
-Xms128m 
-Xmx4096m
-Xss1024k
-XX:MetaspaceSize=512m
-XX:+PrintCommandLineFlags
-XX:+PrintGCDetails
-XX:+UsParallGC(jdk8 默认配置就是并行垃圾回收器)
~~~

## GC垃圾回收
~~~
GC垃圾回收   新生代

(设置jvm参数  -Xms10m -Xmx10m -XX:+PrintGCDetails)
(程序)
-XX:+PrintGCDetails (输出详细GC收集日志信息)
    GC()
    FullGC()

[GC (Allocation Failure) [PSYoungGen: 2048K->504K(2560K)] 2048K->957K(9728K), 0.0011659 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 

[GC (Allocation Failure) [PSYoungGen:   : GC类型
2048K                                   : YoungGC前新生代内存占用
504K                                    : YoungGC后新生代内存占用
(2560K)                                 : 新生代总共大小

2048K                                   : YoungGC前JVM堆内存占用
957K                                    : YoungGC后JVM堆内存占用
(9728K)                                 : JVM堆总大小

0.0011659 secs                          : YoungGC耗时
Times: user=0.00                        : YoungGC用户耗时
sys=0.00                                : YoungGC系统耗时
real=0.00 secs                          : YoungGC实际耗时
~~~

## FullGC垃圾回收
~~~
FullGC垃圾回收  老年代

[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 1022K->1004K(7168K)] 1022K->1004K(9728K), [Metaspace: 3296K->3296K(1056768K)], 0.0075171 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 


规律:
[名称: GC前内存占用 -> GC后内存占用 该区内存总大小
~~~

