package com.mdh.interview.subject.volatileCode;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile可见性
 * 1. 假如 int number = 0; number变量之前根本没有添加volatile关键字修饰
 * 2. 改成 volatile int number = 0;
 *
 * volatile不保证原子性
 *  原子性： 原子性就是指该操作是不可再分的。不论是多核还是单核，具有原子性的量，同一时刻只能有一个线程来对它进行操作。
 *          简而言之，在整个操作过程中不会被线程调度器中断的操作，都可认为是原子性。比如 a = 1；
 *  非原子性的操作： a++(在多线程下不保原子性)    a+=1
 *  a++ 有三步操作：从左到右先从主内存读取a拷贝到线程的工作空间   计算+1   在主内存中写入
 *
 * 解决保证原子性：
 *      1： 加synchronized
 *      2： 使用JUC下面的AtomicInteger
 */
public class VolatileDemo {
    public static void main(String[] args) {

        //seeOkByVolatile();

        noAtomicity();
    }

    /**
     * volatile不保证原子性 同一时刻可能多个线程同时访问修改volatile修饰的变量
     */
    private static void noAtomicity() {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }

        //等待之前的线程结束
        // >2 默认的一个是main线程  一个是GC线程
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t finally number value:" +myData.number + ", atomicInteger:" + myData.atomicInteger);
    }

    /**
     * volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
     */
    public static void seeOkByVolatile() {
        // 资源类
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value:" + myData.number);
        },"AAA").start();

        // 第二个线程
        while (myData.number == 0){
            // main线程就一直在这里等待循环，直到number不为0
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over, main get number value:" +  myData.number);
    }
}

class MyData {// MyData.java --> MyData.class --> JVM字节码文件

    volatile int number = 0;

    public void addTo60(){
        this.number = 60;
    }

    // number是加了volatile关键字修饰的，volatile不保证原子性
    // 可以在方法上使用synchronized(同一时刻只有一个线程可以操作)保证原子性
    public void addPlusPlus(){
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        //CAS Unsafe类 自旋锁
        atomicInteger.getAndIncrement();
    }
}
