package com.mdh.interview.subject.casCode;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS ==> compareAndSet
 * 比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.compareAndSet(5, 3);
        System.out.println(atomicInteger.get());

        atomicInteger.getAndIncrement();
    }
}
