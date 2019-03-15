package com.mdh.thread.concurrent.synchronizedpackage;

/**
 * synchronized 关键字
 * 同步方法 - 原子性
 * 加锁的目标:就是为了保证操作的原子性
 *
 * @Author: madonghao
 * @Date: 2019/3/15 17:43
 */
public class SynchronizedTest03 implements Runnable {

    private int count = 0;

    @Override
    public synchronized void run() {
        System.out.println(Thread.currentThread().getName() + ",count = " + count++);
    }

    public static void main(String[] args) {
        SynchronizedTest03 test = new SynchronizedTest03();
        for (int i= 0; i < 5; i++){
            new Thread(test, "Thread - " + i).start();
        }
    }
}
