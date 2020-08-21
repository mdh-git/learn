package com.mdh.interview.subject.volatileCode;

import java.util.concurrent.atomic.AtomicInteger;

public class Add {
    public static void main(String[] args) {
        System.out.println(1);
    }
    volatile int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    public void add(){
        num++;
    }

    public void atomicAdd(){
        atomicInteger.incrementAndGet();
    }

}
