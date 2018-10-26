package com.mdh.future;

/**
 *
 * @author madonghao
 * @date 2018/10/22
 */
public class ThreadSafeTest {

    public static int a = 0;
    public static void increase() { a++;
        System.out.println(a); }
    public static void main (String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 100; j++) { increase(); } } });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() { for (int j = 0; j < 100; j++) { increase(); } } });
        t1.start();
        t2.start(); }
}
