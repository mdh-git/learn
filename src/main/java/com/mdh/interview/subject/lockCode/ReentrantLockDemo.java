package com.mdh.interview.subject.lockCode;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MDH
 * 2020/8/31 23:51
 * 可重入锁(也叫递归锁)
 *
 * 指的是同一个线程外层函数获得锁之后,内层递归函数仍然能获取该锁的代码,
 * 在同一线程在外层方法获取锁的时候,在进入内层方法会自动获取锁
 *
 * 也就是说,线程可以进入任何一个他已经拥有的锁同步着的代码块
 *
 * t1  sendEMS
 * t1  ###   sendEmails
 * t2  sendEMS
 * t2  ###   sendEmails
 *
 * 可重入锁的意义之一在于防止死锁。
 */
public class ReentrantLockDemo {
    public static void main(String[] args){
        Phone phone = new Phone();

        //synchronizedDemo(phone);

        ReentrantLockDemo(phone);
    }

    /**
     * 验证synchronized是可重入锁
     * @param phone
     */
    private static void synchronizedDemo(Phone phone) {
        new Thread(() -> {
            try {
                phone.sendEMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                phone.sendEMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }

    /**
     * 验证ReentrantLock是可重入锁
     * @param phone
     */
    private static void ReentrantLockDemo(Phone phone) {
        Thread t3 = new Thread(phone, "t3");
        Thread t4 = new Thread(phone, "t4");
        t3.start();
        t4.start();
    }
}

class Phone implements Runnable{
    public synchronized void sendEMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "  sendEMS");
        sendEmails();
    }

    public synchronized void sendEmails() throws Exception{
        System.out.println(Thread.currentThread().getName() + "  ###   sendEmails");
    }

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        getReentrantLock();
    }

    public void getReentrantLock() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "  getReentrantLock()");
            setReentrantLock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void setReentrantLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "  setReentrantLock()");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
