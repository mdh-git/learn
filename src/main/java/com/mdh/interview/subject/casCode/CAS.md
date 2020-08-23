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