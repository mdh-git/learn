package com.mdh.interview.subject.blockingDeque;

import java.util.concurrent.Semaphore;

/**
 * 写三个线程打印 "ABC"，一个线程打印 A，一个线程打印 B，一个线程打印 C，一共打印 10 轮。
 *
 * 基于Semaphore实现
 */
public class PrintABC {

    private final int max;

    private final Semaphore semaphoreA = new Semaphore(1);

    private final Semaphore semaphoreB = new Semaphore(0);

    private final Semaphore semaphoreC = new Semaphore(0);

    public PrintABC(int max) {
        this.max = max;
    }

    public void printA(){
        print("A",semaphoreA, semaphoreB);
    }

    public void printB(){
        print("B",semaphoreB, semaphoreC);
    }

    public void printC(){
        print("C",semaphoreC, semaphoreA);
    }

    public void print(String alphabet, Semaphore cur, Semaphore next){
        for (int i = 0; i < max; i++) {
            try {
                cur.acquire();
                System.out.println(Thread.currentThread().getName() + ":" + alphabet);

                // 传递给下一个
                next.release();
            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
                return;
            }
        }
    }

    public static void main(String[] args) {

        PrintABC print = new PrintABC(10);
        Thread t1 = new Thread(print::printA, "threadA");
        Thread t2 = new Thread(print::printB, "threadB");
        Thread t3 = new Thread(print::printC, "threadC");

        t1.start();
        t2.start();
        t3.start();
    }
}
