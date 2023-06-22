package com.mdh.threadlocal;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程通信： 条件判断
 * @author MDH
 * @date 2023/6/22 14:47
 */
public class ConditionDemo {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread producerThread = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "_Producer:" + i);
                    condition.signal();
                    condition.await();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        },"ProducerThread");

        Thread consumeThread = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "_Consume:" + i);
                    condition.signal();
                    condition.await();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        },"ConsumeThread");

        producerThread.start();
        consumeThread.start();
        producerThread.join();
        consumeThread.join();
        System.out.println("结束");
    }
}
