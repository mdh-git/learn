package com.mdh.interview.subject.blockingDeque;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MDH
 * 2020/9/19 12:46
 *
 * 题目：多线程之间按顺序调用,实现A->B->C三个线程启动,要求如下:
 * A打印5次,B打印10次,C打印15次
 * 紧接着
 * A打印5次,B打印10次,C打印15次
 * ...
 * 循环10次
 */
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.printInfo(1);
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.printInfo(2);
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareResource.printInfo(3);
            }
        },"C").start();
    }
}

class ShareResource {
    //A:1 B:2 C:3
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void printInfo(int num){

        lock.lock();
        try {
            // 1 判断
            while (number != num){
                if(num == 1){
                    c1.await();
                } else if(num == 2){
                    c2.await();
                } else if(num == 3){
                    c3.await();
                }
            }

            // 2 干活
            if(num == 1){
                System.out.println(Thread.currentThread().getName() + "打印5次");
                // 3 通知
                number = 2;
                c2.signal();
            } else if(num == 2){
                System.out.println(Thread.currentThread().getName() + "打印10次");
                number = 3;
                c3.signal();
            } else if(num == 3){
                System.out.println(Thread.currentThread().getName() + "打印15次");
                number = 1;
                c1.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
