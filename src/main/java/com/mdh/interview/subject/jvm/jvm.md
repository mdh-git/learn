# JVM

## JVM内存结构
~~~

Class files    ->  类装载器子系统  Class loader
                             |
                             |
              运行时数据区(Runtime Data Area)

  线程共有                                         线程私有

方法区(Method Area)                 java虚拟机栈(java stack)         本地方法栈(Native Method stack)

堆(Heap)                             程序计数器(Program Counter Register)


java8 之后把永久代改成了元空间
~~~

## 程序计数器(Program Counter Register)
~~~
线程私有的，内部保存的字节码的行号，用于记录正在执行的字节码指令的地址。
javap -v XX.class  打印堆栈大小，局部变量的数量和方法的参数
~~~

## 堆(Heap)
~~~
线程共享的区域：主要用来保存对象实例、数组等，当堆中没有内存空间可分配给实例，也无法在扩展时，则抛出OutOfMemoryError异常

年轻代(1/3):Eden  S0  S1   8:1:1
    Eden区和两个大小严格相同的Survivor区，根据JVM的策略，在经过多次垃圾收集后，依然存活于Survivor的对象将被移动到老年代的区间
老年代(2/3):
    主要保存生命周期长的对象，一般是一些老的对象
~~~

## 元空间
~~~
Java1.7 在堆中的永久代
Java1.8 本地内存的元空间（防止内存溢出）

保存类的信息、静态变量、常量、编译后的代码
    Class   ClassLoader   运行时常量池
~~~

## 方法区
~~~
方法区(Method Area)  是各个线程共享的内存区域
主要存储类的信息、运行时常量池、静态变量、即时编译后的代码
虚拟机启动的时候创建，关闭虚拟机时释放
如果方法区中的内存无法满足分配请求，则会抛出java.lang.OutOfMemoryError: Metaspace
~~~

## 常量池
~~~
可以看做是一张表，虚拟机指令根据这张敞亮表找到要执行的类名、方法名、参数类型、字面量等信息
通过javap -v XXX.class   查看字节码结构（类的基本信息、常量池、方法定义）

~~~

## 运行时常量池
~~~
常量池是XXX.class文件中，当该类被加载，它的常量池信息会被放入运行时常量池，并把里面的符号地址变为真实地址
~~~



## 直接内存
~~~
并不属于JVM中的内存结构，不由JVM进行管理。
是虚拟机的系统内存，常用于NIO操作时，用于数据缓冲区，它分配回收成本较高，但读写性能高
~~~


## java虚拟机栈
~~~
会出现OOM的情况，入栈太多 java.lang.StackOverflowError

1.每个线程运行时所需要的内存，称为虚拟机栈，先进后出的原则（多个线程会有多个虚拟机栈）
2.每个栈由多个栈帧（frame）组成，对应着每次方法调用时所占用的内存
    栈帧：参数、局部变量、返回地址
3.每个线程只能有一个活跃栈帧，对应着当前正在执行的那个方法

问题1:栈内存分配越大越好吗？
    默认的栈内存通常是1024k，栈帧过大会导致线程数变少
        例如:机器总内存为512m，目前能活动的线程数则为512个，如果把栈内存改为2018k，那么活动的栈帧就会减半

问题2:垃圾回收是否涉及栈内存？
    垃圾回收主要是指堆内存，当栈帧出栈以后，内存就会释放
    
问题3:方法内的局部变量是否是线程安全的？
    1.如果方法内局部变量没有逃离方法的作用范围（返回是void），是线程安全的
    2.如果是局部变量引用了对象（形参中是对象），并逃离方法的范围（返回是对象），需要考虑线程安全
   
问题4:栈内存溢出的情况？
    1.栈帧过多导致栈内存溢出，经典案例：递归调用
    2.栈帧过大导致内存溢出
~~~

## 堆和栈的区别？
~~~
栈内存:
    存储局部变量和方法调用，不会进行GC垃圾回收
    线程私有的
    栈空间不足:java.lang.StackOverflowError

堆内存:
    存储Java对象和数组，堆会进行GC垃圾回收
    线程共享的
    堆内存不足:java.lang.OutOfMemoryError
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




## 常量池 运行时常量池 字符串常量池
~~~
1.Class Constant Pool 常量池
    Static  在.class文件里的静态图纸         符号地址
    
    
2.Runtime Constant Pool 运行时常量池
    Loaded  类加载后，真实数据               真实地址


3.String Table  字符串常量池
    (HashTable结构)
~~~

## String Table
~~~
JDK 1.6   String Table 是在PermGen(永久代)中    
            StringTable挤在永久代中，空间小，难回收
            
            
            
JDK 1.7+  String Table 是在Heap中
            空间大，频繁GC
            
            
StringTable(字符串常量池)本质是HashTable，如果过多字符串，桶(Bucket)不够用，链表就会巨长，存取变成遍历链表，CPU直接飙升
如果要存海量数据到常量池，调整桶的大小： -XX:StringTableSize=N
~~~

## intern()问题
~~~
// s指向堆中的"11"对象

String s = new String("1") + new String("1");

// 关键点
s.intern();

String x = "11";
s == x


在JDK6  为False，Copy Mode(复制副本到PermGen)

在JDK7+ 为true  Reference Mode(只存引用)，因为在StringTable就在堆里，直接引用对象，不复制
~~~
