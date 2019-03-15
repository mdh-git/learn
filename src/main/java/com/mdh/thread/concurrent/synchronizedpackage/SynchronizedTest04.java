package com.mdh.thread.concurrent.synchronizedpackage;

/**
 * synchronized 关键字
 * 同步方法 - 同步方法和非同步方法
 * @Author: madonghao
 * @Date: 2019/3/15 18:20
 */
public class SynchronizedTest04 {

    public synchronized void m1(){
        System.out.println("public synchronized void m1() start");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("public synchronized void m1() end");
    }

    public void m2(){
        System.out.println("public void m2() start");

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("public void m2() end");
    }

    public static class MyThread01 implements Runnable{
        int i;
        SynchronizedTest04 t;
        public  MyThread01(int i, SynchronizedTest04 t){
            this.i = i;
            this.t = t;
        }
        @Override
        public void run() {
            if(i == 0){
                t.m1();
            } else {
                t.m2();
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedTest04 test = new SynchronizedTest04();
        //new Thread(new SynchronizedTest04(0, test)).start();
    }
}
