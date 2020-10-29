package com.mdh.interview.subject.topic;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MDH
 * 2020/10/29 21:51
 */
public class AQSDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        //举例 一个银行办理业务的案例来模拟AQS如何进行线程的管理和通知唤醒机制

        //3个线程模拟3个来银行网点,受理窗口办理业务


        // A 顾客就是第一个顾客,此时受理窗口没有任何人,A可以直接去办理
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("------A Thread come in");
                try {
                    TimeUnit.MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }, "A").start();


        // B顾客 就是第二个顾客,第2个线程----> 由于受理窗口只有一个(只能一个线程持有锁),此时B只能等待
        // 进入候客区
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("------B Thread come in");
            } finally {
                lock.unlock();
            }
        }, "B").start();

        // C顾客 就是第三个顾客,第3个线程----> 由于受理窗口只有一个(只能一个线程持有锁),此时C只能等待
        // 进入候客区
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("------C Thread come in");
            } finally {
                lock.unlock();
            }
        }, "C").start();
    }
}
