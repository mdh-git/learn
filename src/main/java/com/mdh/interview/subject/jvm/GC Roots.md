#GC Roots
~~~
内存中已经不再使用到的空间就是垃圾

判断一个对象是否可以被回收？
 引用计数法(java中不使用)
    java中,引用和对象是有关联的。如果要操作对象则必须用引用进行。
    因此,很显然一个简单的办法是通过引用计数来判断一个对象是否回收。
    给对象添加一个引用计数器,每当有一个地方引用它,计数器值+1,每当有一个地方失效时,计数器值-1
    任何时刻计数器值为零的对象就是不可能再被使用的,那么这个对象就是可回收对象。
    
缺点: 很难解决对象之间相互循环引用的问题

(2) 枚举根节点做可达性分析(根搜索路径) 从GC Roots开始分析

为了解决引用计数法的循环引用问题,java使用了可达性分析的方法

跟踪(Tracing)

· 复制(Copying)
· 标记-清除(Mark-Sweep)
· 标记-压缩(Mark-COmpact)
· Mark-Sweep(-Compact)
所谓"GC Roots"或者说tracing GC的"根集合" 就是一组必须活跃的引用。

基本思路就是通过一系列名为"GC Roots",从这个被称为GC Roots的对象开始向下搜素,如果一个对象到GC Roots
没有任何引用链时,则说明此对象不可用。也即给定一个集合的引用作为跟出发,通过引用关系遍历对象图,能被遍历到的
(可到达的)对象就被判定为存活,没有被遍历的自然被判定为死亡。
~~~

## 那些对象可以作为GC Roots
~~~
(1): 虚拟机栈(栈帧中的局部变量区,也叫做局部变量表)中引用的对象。
(2): 方法区中的类静态属性引用的对象。
(3): 方法区常量引用的对象。
(4): 本地方法栈中JNI(Native方法)引用的对象。
~~~