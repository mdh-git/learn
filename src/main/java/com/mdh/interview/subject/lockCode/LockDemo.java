package com.mdh.interview.subject.lockCode;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo implements Serializable {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
    }
}
