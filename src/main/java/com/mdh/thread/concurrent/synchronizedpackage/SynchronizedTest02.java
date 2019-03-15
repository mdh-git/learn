package com.mdh.thread.concurrent.synchronizedpackage;

/**
 * Synchronized关键字
 * 同步方法-static
 * 静态同步方法,锁的是当前类的类对象。在本代码中就是SynchronizedTest02.class
 * 静态锁的是类的所有对象,非静态锁的是创建出来的对象
 * @Author: madonghao
 * @Date: 2019/3/15 17:26
 */
public class SynchronizedTest02 {
    private static int staticCount = 0;

    public static synchronized void testSyn4(){
        System.out.println(Thread.currentThread().getName() + ",staticCount = " + staticCount++);
    }

    public static void testSyn5(){
        synchronized (SynchronizedTest02.class){
            System.out.println(Thread.currentThread().getName() + ",staticCount = " + staticCount++);
        }
    }
}
