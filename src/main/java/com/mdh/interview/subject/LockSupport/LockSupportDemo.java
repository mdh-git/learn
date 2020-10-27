package com.mdh.interview.subject.LockSupport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MDH
 * 2020/10/27 22:34
 * 线程等待唤醒通知的约束
 * 1.线程先要获得并持有锁,必须在锁块(synchronized或Lock)中
 * 2.必须要先等待后唤醒,线程才能够被唤醒
 */
public class LockSupportDemo {

    static Object objectLock = new Object();
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    public static void main(String[] args) {

        //synchronizedWaitNotify();

        //conditionAwaitSignal();

        Thread a = new Thread(() -> {
            try {
                Thread.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "---------come in" + System.currentTimeMillis());
            LockSupport.park();//被阻塞...等待通知等待放行,它需要许可证
            System.out.println(Thread.currentThread().getName() + "\t" + "---------被唤醒" + System.currentTimeMillis());
        }, "a");
        a.start();

        Thread b = new Thread(() -> {
            LockSupport.unpark(a);//
            System.out.println(Thread.currentThread().getName() + "\t" + "---------通知");
        }, "b");
        b.start();
    }

    private static void conditionAwaitSignal() {
        new Thread(() -> {
//            try {
//                Thread.sleep(3L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "\t" + "---------come in");
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "---------被唤醒");
            lock.unlock();
        },"A").start();

        new Thread(() -> {
            lock.lock();
            condition.signal();
            System.out.println(Thread.currentThread().getName() + "\t" + "---------通知");
            lock.unlock();
        },"B").start();
    }

    /**
     *  synchronized 中 先wait()  后notify()
     *  wait()方法和notify()方法,两个都去掉同步代码块出现异常情况
     */
    private static void synchronizedWaitNotify() {
        new Thread(() -> {
            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName() + "\t" + "---------come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---------被唤醒");
            }
        },"A").start();

        new Thread(() -> {
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t" + "---------通知");
            }
        },"B").start();
    }
}
