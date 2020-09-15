package com.mdh.interview.subject.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author MDH
 * 2020/9/15 21:09
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        //test01();

        test02();
    }

    /**
     * 使用CountDownLatch保证之前的线程先被执行完成
     */
    private static void test02() {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName() + "\t 上完自习,离开教室。");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t 班长最后关门走人");
    }

    /**
     * 多个线程抢占资源
     */
    private static void test01() {
        for (int i = 1; i <= 6; i++) {
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName() + "\t 上完自习,离开教室。");
            }, String.valueOf(i)).start();
        }
        System.out.println(Thread.currentThread().getName() + "\t 班长最后关门走人");
    }
}
