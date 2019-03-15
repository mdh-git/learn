package com.mdh.thread.concurrent.synchronizedpackage;

/**
 *
 * synchronized:锁的是对象
 * 可能锁的对象包括: this, 临界资源对象(多个线程都能访问到的对象), class类对象
 * synchronized(this) 与 synchronized方法都是锁当前对象
 *
 * @Author: madonghao
 * @Date: 2019/3/15 16:47
 */
public class SynchronizedTest01 {
    /**
     * 多线程创建 引用都存在栈帧中(基本类型是存在栈帧里。对象是在堆内存中)
     */
    private int count = 0;
    private Object object = new Object();

    public void testSyn1() {
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + ",count = " + count++);
        }
    }

    public void testSyn2() {
        synchronized (this){
            System.out.println(Thread.currentThread().getName() + ",count = " + count++);
        }
    }

    /**
     * 等同与testSyn2  锁的是this
     */
    public synchronized void testSyn3() {
        System.out.println(Thread.currentThread().getName() + ",count = " + count++);
    }

    public static void main(String[] args) {
        SynchronizedTest01 test = new SynchronizedTest01();
        test.testSyn1();
    }
}
