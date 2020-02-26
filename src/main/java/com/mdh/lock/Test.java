package com.mdh.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author madonghao
 * @create 2020-01-20 16:18
 **/
public class Test {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.newCondition();
    }
}
