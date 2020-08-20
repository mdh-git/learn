package com.mdh.interview.subject.volatileCode;

import java.util.concurrent.TimeUnit;

/**
 * volatile可见性
 * 1. 假如 int number = 0; number变量之前根本没有添加volatile关键字修饰
 * 2. 改成 volatile int number = 0;
 */
public class VolatileDemo {
    public static void main(String[] args) {

        // 资源类
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value:" + myData.number);
        },"AAA").start();

        // 第二个线程
        while (myData.number == 0){
            // main线程就一直在这里等待循环，直到number不为0
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over, main get number value:" +  myData.number);
    }
}

class MyData {

    volatile int number = 0;

    public void addTo60(){
        this.number = 60;
    }
}
