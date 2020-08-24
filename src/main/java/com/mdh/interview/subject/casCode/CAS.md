# CAS
~~~
expect 期望值
update 更新值
public final boolean compareAndSet(int expect, int update) {
    return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
}

CAS(Compare-And-Swap),它是一条CPU并发原语。
它的功能是判断内存某个位置的值是否为预期值,如果是则更改为新的值,这个过程是原子的。

CAS并发原语体现在JAVA语言中就是sun.misc.Unsafe类中的各个方法。调用Unsafe类中的CAS方法,
JVM会帮助我们实现出CAS汇编指令。这是一种完全依赖于硬件的功能,通过它实现了原子操作。再次强调,
由于CAS是一种系统原语,原语属于操作系统用语范畴,是由若干条指令组成,用于完成某个功能的一个过程,
并且原语的执行必须是连续的,在执行过程中不允许被中断,也就是说CAS是一条CPU的原子操作,不会造成
所谓的数据不一致问题。
public final int getAndIncrement() {
    return unsafe.getAndAddInt(this, valueOffset, 1);
}

//unsafe.getAndAddInt
public final int getAndAddInt(java.lang.Object o, Long l, int i){}

public final int getAndAddInt(Object var1, Long var2, int var4){
    int var5; 
    do{
        var5 = this.getIntVolatile(var1, var2);
    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
    return var5;
}
~~~

## atomicInteger.getAndIncrement() 
~~~
源码分析
自旋锁 unsafe类(rt.jar\sun\misc)

Unasfe类是CAS的核心类,由于java方法无法直接访问底层系统,需要通过本地(native)方法来访问,
Unsafe类相当于一个后门，基于该类可以直接操作特定内存的数据。Unsafe类存在于rt.jar\sun\misc包中,
其内部方法操作可以像C的指针一样直接操作内存,java中CAS操作打的执行依赖于Unsafe类的方法。
注意:Unsafe类中的所有方法都是native修饰的,也就是说Unsafe类中的方法都直接调用操作系统底层资源执行相应任务。

private static final Unsafe unsafe = Unsafe.getUnsafe();
private static final long valueOffset;
static {
    try {
        valueOffset = unsafe.objectFieldOffset
            (AtomicInteger.class.getDeclaredField("value"));
    } catch (Exception ex) { throw new Error(ex); }
}
private volatile int value;
变量value用volatile修饰,保证多线程之间的内存可见性。

this 当前对象
valueOffset  表示该变量值在内存中的偏移地址,因为Unsafe就是根据内存偏移地址获取数据的。
public final int getAndIncrement() {
    return unsafe.getAndAddInt(this, valueOffset, 1);
}    
~~~

## 使用CAS,不使用synchronized
~~~
使用synchronized加锁,一致性得到了保证,但是在同一时刻只能有一个线程访问,并发性下降

CAS do while  使用(自旋锁),一直等待
~~~

## CAS缺点
~~~
1. 循环时间长,开销大(允许多线程互相修改)
    getAndAddInt方法下 do while
2. 只能保证一个共享变量的原子操作
    加锁可以保证一段变量的原子操作
3. ABA问题 原子引用更新
    CAS算法实现一个重要前提需要取出内存中某个时刻的数据并在当下时刻比较替换,
    那么在这个时间差内会导致数据的变化。

例如：一个线程one从内存位置V中取出A,这时候另外一个线程two也从内存中取出A,
并且线程two进行了一些操作将变量变成了B,然后线程two又将V位置的数据变成A,
这时候线程one进行CAS操作发现内存中仍然是A,然后线程one操作成功。

尽管线程one的CAS操作成功,但是不代表这个过程就是没有问题的。
~~~

## 如何解决ABA问题
~~~
 原子引用 +  新增一个机制,修改版本号(类似时间戳)
~~~