package com.mdh.interview.subject.callable;

import java.util.concurrent.TimeUnit;

/**
 * @author MDH
 * 2020/9/22 21:43
 * 死锁是指两个或者两个以上的进程在执行过程中,因争夺资源而造成的一种相互等待的现象,
 * 如无外力干涉那么它们都将无法推进下去
 *
 * java里面自带的两个工具  C:\Program Files\Java\jdk1.8.0_101\bin
 * jps.exe       查看进程编号   jps -l
 *
 * D:\workspaceidea\code\learn>jps -l
 *
 * jstack.exe    查看堆栈信息   jstack 11624(进程号)
 *
 * linux       ps -ef|grep xxx    ls -l
 * window下的java运行程序    jps    jps -l
 *
 *
 *
 * D:\workspaceidea\code\learn>jps -l
 * 17376 sun.tools.jps.Jps
 * 14388 org.jetbrains.jps.cmdline.Launcher
 * 15620
 * 11624 com.mdh.interview.subject.callable.DeadLockDemo
 *
 * D:\workspaceidea\code\learn>jstack 11624
 * 2020-09-22 22:06:21
 * Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.101-b13 mixed mode):
 *
 * "DestroyJavaVM" #22 prio=5 os_prio=0 tid=0x0000000002bb6000 nid=0x3eac waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "BBB" #21 prio=5 os_prio=0 tid=0x000000001bc47000 nid=0x33e0 waiting for monitor entry [0x000000001d1cf000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 *         at com.mdh.interview.subject.callable.HoldLockThread.run(DeadLockDemo.java:42)
 *         - waiting to lock <0x00000000d5cf25b8> (a java.lang.String)
 *         - locked <0x00000000d5cf25f0> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 * "AAA" #20 prio=5 os_prio=0 tid=0x000000001bc45800 nid=0x46f4 waiting for monitor entry [0x000000001d0cf000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 *         at com.mdh.interview.subject.callable.HoldLockThread.run(DeadLockDemo.java:42)
 *         - waiting to lock <0x00000000d5cf25f0> (a java.lang.String)
 *         - locked <0x00000000d5cf25b8> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 * "Service Thread" #19 daemon prio=9 os_prio=0 tid=0x000000001b976800 nid=0x3060 runnable [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C1 CompilerThread11" #18 daemon prio=9 os_prio=2 tid=0x000000001b8b6800 nid=0x35d8 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C1 CompilerThread10" #17 daemon prio=9 os_prio=2 tid=0x000000001b8bb000 nid=0x3780 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C1 CompilerThread9" #16 daemon prio=9 os_prio=2 tid=0x000000001b8ba800 nid=0x21d4 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C1 CompilerThread8" #15 daemon prio=9 os_prio=2 tid=0x000000001b8b6000 nid=0x4714 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread7" #14 daemon prio=9 os_prio=2 tid=0x000000001b8bc000 nid=0x3e50 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread6" #13 daemon prio=9 os_prio=2 tid=0x000000001b8bc800 nid=0x1254 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread5" #12 daemon prio=9 os_prio=2 tid=0x000000001b8ab000 nid=0xdd8 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread4" #11 daemon prio=9 os_prio=2 tid=0x000000001b8a6800 nid=0x1fdc waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread3" #10 daemon prio=9 os_prio=2 tid=0x000000001b891800 nid=0x3fa4 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x000000001b88e000 nid=0x960 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x000000001b88a000 nid=0x3750 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x000000001b889800 nid=0x16c4 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x000000001b888000 nid=0x443c runnable [0x000000001c0ce000]
 *    java.lang.Thread.State: RUNNABLE
 *         at java.net.SocketInputStream.socketRead0(Native Method)
 *         at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
 *         at java.net.SocketInputStream.read(SocketInputStream.java:170)
 *         at java.net.SocketInputStream.read(SocketInputStream.java:141)
 *         at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
 *         at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
 *         at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
 *         - locked <0x00000000d5c5e0c8> (a java.io.InputStreamReader)
 *         at java.io.InputStreamReader.read(InputStreamReader.java:184)
 *         at java.io.BufferedReader.fill(BufferedReader.java:161)
 *         at java.io.BufferedReader.readLine(BufferedReader.java:324)
 *         - locked <0x00000000d5c5e0c8> (a java.io.InputStreamReader)
 *         at java.io.BufferedReader.readLine(BufferedReader.java:389)
 *         at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:61)
 *
 * "Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x0000000019b6a800 nid=0x1aa8 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000019b55000 nid=0x3ea8 runnable [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *
 * "Finalizer" #3 daemon prio=8 os_prio=1 tid=0x00000000181b1800 nid=0x18f8 in Object.wait() [0x000000001b56e000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 *         at java.lang.Object.wait(Native Method)
 *         - waiting on <0x00000000d5988ee0> (a java.lang.ref.ReferenceQueue$Lock)
 *         at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
 *         - locked <0x00000000d5988ee0> (a java.lang.ref.ReferenceQueue$Lock)
 *         at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
 *         at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)
 *
 * "Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x00000000181a9000 nid=0x343c in Object.wait() [0x000000001b46f000]
 *    java.lang.Thread.State: WAITING (on object monitor)
 *         at java.lang.Object.wait(Native Method)
 *         - waiting on <0x00000000d5986b50> (a java.lang.ref.Reference$Lock)
 *         at java.lang.Object.wait(Object.java:502)
 *         at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
 *         - locked <0x00000000d5986b50> (a java.lang.ref.Reference$Lock)
 *         at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
 *
 * "VM Thread" os_prio=2 tid=0x0000000019b04800 nid=0x9b8 runnable
 *
 * "GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000002bcb800 nid=0x2dc0 runnable
 *
 * "GC task thread#1 (ParallelGC)" os_prio=0 tid=0x0000000002bcd000 nid=0x3f9c runnable
 *
 * "GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000002bce800 nid=0x2c94 runnable
 *
 * "GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000002bd0000 nid=0x3c74 runnable
 *
 * "GC task thread#4 (ParallelGC)" os_prio=0 tid=0x0000000002bd3800 nid=0x3284 runnable
 *
 * "GC task thread#5 (ParallelGC)" os_prio=0 tid=0x0000000002bd4800 nid=0x1b5c runnable
 *
 * "GC task thread#6 (ParallelGC)" os_prio=0 tid=0x0000000002bd8000 nid=0x3aa0 runnable
 *
 * "GC task thread#7 (ParallelGC)" os_prio=0 tid=0x0000000002bd9000 nid=0x47c0 runnable
 *
 * "GC task thread#8 (ParallelGC)" os_prio=0 tid=0x0000000002bda000 nid=0x2f24 runnable
 *
 * "GC task thread#9 (ParallelGC)" os_prio=0 tid=0x0000000002bdb800 nid=0x1dc0 runnable
 *
 * "GC task thread#10 (ParallelGC)" os_prio=0 tid=0x0000000002bdc800 nid=0x2394 runnable
 *
 * "GC task thread#11 (ParallelGC)" os_prio=0 tid=0x0000000002bdf800 nid=0x4638 runnable
 *
 * "GC task thread#12 (ParallelGC)" os_prio=0 tid=0x0000000002be1000 nid=0x41bc runnable
 *
 * "VM Periodic Task Thread" os_prio=2 tid=0x000000001b97b000 nid=0x42b8 waiting on condition
 *
 * JNI global references: 16
 *
 *
 * Found one Java-level deadlock:
 * =============================
 * "BBB":
 *   waiting to lock monitor 0x00000000181aea88 (object 0x00000000d5cf25b8, a java.lang.String),
 *   which is held by "AAA"
 * "AAA":
 *   waiting to lock monitor 0x00000000181b1268 (object 0x00000000d5cf25f0, a java.lang.String),
 *   which is held by "BBB"
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "BBB":
 *         at com.mdh.interview.subject.callable.HoldLockThread.run(DeadLockDemo.java:42)
 *         - waiting to lock <0x00000000d5cf25b8> (a java.lang.String)
 *         - locked <0x00000000d5cf25f0> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:745)
 * "AAA":
 *         at com.mdh.interview.subject.callable.HoldLockThread.run(DeadLockDemo.java:42)
 *         - waiting to lock <0x00000000d5cf25f0> (a java.lang.String)
 *         - locked <0x00000000d5cf25b8> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 * Found 1 deadlock.
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA, lockB), "AAA").start();
        new Thread(new HoldLockThread(lockB, lockA), "BBB").start();

        // jps.java
    }
}

class HoldLockThread implements Runnable {

    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有:" + lockA + ",尝试获取:" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有:" + lockB + ",尝试获取:" + lockA);
            }
        }
    }
}
