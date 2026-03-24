# Synchronized底层实现

~~~
https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/multi-thread/2020%E6%9C%80%E6%96%B0Java%E5%B9%B6%E5%8F%91%E8%BF%9B%E9%98%B6%E5%B8%B8%E8%A7%81%E9%9D%A2%E8%AF%95%E9%A2%98%E6%80%BB%E7%BB%93.md
~~~


## Synchronized
~~~
Synchronized 是 Java 中最基础的内置锁机制，其底层实现经历了多次优化。
在 HotSpot 虚拟机中，它的核心原理可以概括为：基于对象头（Object Header）的 Mark Word 存储锁状态，配合 Monitor（监视器）对象实现互斥，并通过“锁升级”机制在不同场景下平衡性能与安全性。
~~~

## Java 对象头 (Object Header)
~~~
synchronized 锁住的本质是对象。每个 Java 对象在内存中都包含一个对象头，其中最关键的部分是 Mark Word。

Mark Word 的结构（64位虚拟机为例）
Mark Word 是一个 64 位的字段，它会随着对象状态的变化而存储不同的信息。在无锁、偏向锁、轻量级锁、重量级锁四种状态下，其内部结构完全不同：
~~~

| 锁状态 | 标志位 (最后3位) | 内容描述 |
| :--- | :---: | :--- |
| 无锁 | `001` | 存储对象的哈希码 (HashCode)、分代年龄、GC 标记等。 |
| 偏向锁 | `101` | 存储偏向线程 ID、偏向时间戳、记录重入计数等。 |
| 轻量级锁 | `000` | 存储指向栈中锁记录 (Lock Record) 的指针。 |
| 重量级锁 | `100` | 存储指向堆中 Monitor 对象 (ObjectMonitor) 的指针。 |
| GC 标记 | `011` | 表示对象正在被 GC 标记。 |
| 不可用 | `111` | 表示对象不可用。 |
关键点：JVM 通过检查 Mark Word 的最后几位标志位，就能瞬间判断当前对象处于什么锁状态，以及锁被谁持有。

## 字节码指令
~~~
javap -v xx.class  查看class字节码信息


1.同步代码块 (synchronized(obj))
    使用 monitorenter 和 两次 monitorexit 指令。
    monitorenter：尝试获取锁。如果对象无锁或当前线程已持有锁，则成功；否则阻塞。
    monitorexit：释放锁。确保即使代码抛出异常，也能通过异常表机制释放锁。
    
2.同步方法 (public synchronized void method())：
    不直接使用上述指令，而是在方法访问标志中设置 ACC_SYNCHRONIZED。
    JVM 在调用该方法时，会自动检查该标志并隐式执行加锁/解锁逻辑。
~~~

## Synchronized关键字的底层原理
~~~
Synchronized【对象锁】采用互斥的方式让同一时刻至多只有一个线程能持有【对象锁】

它的底层由monitor实现的，monitor是jvm级别的对象(C++实现)，线程获得锁需要使用对象(锁)关联monitor
在monitor内部有三个属性，分别是owner、entrylist、waitset
    owner是关联的获得锁的线程，并且只能关联一个线程;
    entrylist关联的是处于阻塞状态的线程;
    waitset关联的是处于Waiting状态的线程
    

Monitor实现的锁属于重量级锁，里面涉及到了用户态和内核态的切换、进程的上下文切换，成本较高，性能比较低。
在JDK1.6引入了两种新型锁机制:偏向锁和轻量级锁，它们的引入是为了解决在没有多线程竞争或基本没有竞争的场景下因使用传统锁机制带来的性能开销问题。

~~~

## 锁升级过程 (Lock Escalation)
~~~
为了减少直接操作系统互斥量（Mutex）带来的性能开销，JVM 引入了偏向锁和轻量级锁。
锁的状态只能单向升级（无锁 -> 偏向 -> 轻量 -> 重量），不能降级（除非全局安全点批量撤销）。

1.第一阶段：偏向锁 (Biased Locking)
    场景：适用于只有一个线程访问同步块的场景（如单线程循环）。
    原理：
        1.当第一个线程访问锁对象时，JVM 将 Mark Word 设置为“偏向模式”，并将当前线程 ID 记录在 Mark Word 中。
        2.后续该线程再次进入同步块时，只需对比 Mark Word 中的线程 ID 是否与自己一致。
        3.一致：无需任何 CAS 操作，直接执行（性能极高，近乎无锁）。
        4.不一致：发生偏向锁撤销，升级为轻量级锁。
    注意：在 JDK 15 及以后版本，偏向锁默认被禁用（因为现代应用多线程竞争激烈，偏向锁撤销开销大），但在老版本或特定配置下仍常见。
        
2.第二阶段：轻量级锁 (Lightweight Locking)
    场景：适用于存在轻微竞争，但线程交替执行（没有同时竞争）的场景。
    原理：
        1.当偏向锁失效或有第二个线程尝试获取锁时，锁升级为轻量级锁。
        2.JVM 会在当前线程的栈帧中创建一个锁记录 (Lock Record)，复制对象头的 Mark Word 到锁记录中。
        3.线程尝试使用 CAS (Compare-And-Swap) 操作，将对象头的 Mark Word 替换为指向栈中锁记录的指针。
        4.CAS 成功：获取锁，执行代码。
        5.CAS 失败：说明有竞争。JVM 会检查对方线程是否持有锁且正在运行。
            若对方未运行（可能只是暂停），尝试自旋等待。
            若竞争激烈或自旋超时，升级为重量级锁。
            
3.第三阶段：重量级锁 (Heavyweight Locking)
    场景：适用于激烈竞争，多个线程同时争夺锁的场景。
    原理：
        1.Mark Word 指向堆内存中的 ObjectMonitor 对象（C++ 实现）。
        2.ObjectMonitor 内部维护了两个队列：
            _EntryList：存放所有正在抢锁但失败的线程（Blocked 状态）。
            _WaitSet：存放调用 wait() 方法后进入等待状态的线程。
        3.获取失败的线程会被操作系统挂起 (Block)，进入内核态，由 OS 负责后续的唤醒。
        4.代价：用户态与内核态的切换开销大，性能最低，但能保证强一致性。
~~~

## Synchronized可重入锁
~~~
Synchronized 是可重入锁。
实现：在 ObjectMonitor 中维护了一个 _count 计数器 和 _owner 指针。
    逻辑：
        线程首次获取锁：_owner 设为当前线程，_count = 1。
        同一线程再次获取：发现 _owner 就是自己，_count + 1。
        释放锁：_count - 1。只有当 _count 归零时，才真正释放锁并唤醒其他线程。
~~~