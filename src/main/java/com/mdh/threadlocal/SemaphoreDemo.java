package com.mdh.threadlocal;

import java.util.concurrent.Semaphore;

/**
 * 线程通信： 信号量
 * @author MDH
 * @date 2023/6/22 14:37
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(0);
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "_Producer:" + i);
                    semaphore.release();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"ProducerThread");

        Thread consumeThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "_Consume:" + i);
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"ConsumeThread");

        producerThread.start();
        consumeThread.start();

    }
}
