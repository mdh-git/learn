package com.mdh.interview.subject.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author MDH
 * 2020/9/15 22:38
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // public CyclicBarrier(int parties, Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> System.out.println("完成,召唤神龙"));
        for (int i = 1; i <= 7; i++) {
            int finalI = i;
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName() + " 收集到第: " + finalI + "号龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
