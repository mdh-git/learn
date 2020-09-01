package com.mdh.interview.subject.lockCode;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDemo implements Serializable {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        synchronized (LockDemo.class){

        }

        ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
    }
}
