# Java的对象结构
~~~
Java 对象在内存中的结构主要由三部分组成:对象头(Object Header)、实例数据(Instance Data)和对齐填充(Padding)
~~~

## 对象头(Object Header)
~~~
对象头包含两类关键信息:
    Mark Word:存储对象自身的运行时元数据，长度在 32 位系统为 32 位，64 位系统为64 位。包含:
        哈希码(HashCode)
        GC 分代年龄(Generational GC Age)
        锁状态标志(如偏向锁、轻量级锁、重量级锁)
        线程持有的锁/偏向线程ID
        偏向时间戳

类型指针:指向方法区中对象的类元数据(Class 对象)，JM 通过它确定对象属于哪个类。64 位系统默认开启压缩指针(-XX:+UseCompressedOops)，此时占4字节否则占8 字节。
~~~

## 实例数据(Instance Data)
~~~
存储对象的所有字段值(包括父类继承的字段)
字段排列顺序受虚拟机分配策略影响:
    基本类型优先:long/double>int/float>short/char>byte/boolean >引用类型。
    父类字段在子类之前。

字段重排序:为节省内存，JM 可能重新排列字段顺序(如将 int 和 boolean 相邻放置以减少对齐填充)
~~~

## 对齐填充(Padding)        
~~~
JVM 要求对象大小必须是 8 字节的整数倍。
    示例对象:-个包畬 int id 和 String name 的类
        对象头:12 字节
        int id:4字节
        String name(引用):4字节
        
总计:20 字节 → 需填充 4 字节 → 最终占用 24 字节。

对象头与实例数据总大小若非 8 字节倍数，则通过填充空白字节对齐

作用: 提升内存访问效率(CPU通常按照块读取内存)
~~~