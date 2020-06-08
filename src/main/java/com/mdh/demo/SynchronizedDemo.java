package com.mdh.demo;

/**
 * @author madonghao
 * @create 2020-05-25 15:11
 **/
public class SynchronizedDemo {
    public void method() {
        synchronized (this) {
            System.out.println("synchronized daimakuai");
        }
    }
}
