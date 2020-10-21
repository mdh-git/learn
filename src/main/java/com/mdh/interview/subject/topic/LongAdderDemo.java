package com.mdh.interview.subject.topic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author madonghao
 * @create 2020-10-21 15:36
 **/
public class LongAdderDemo {
    public static void main(String[] args) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        AtomicInteger integer = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                longAdder.increment();
                integer.incrementAndGet();
            }, "线程" + i).start();
        }
        Thread.sleep(500);
        System.out.println(integer);
        System.out.println(longAdder);
    }
}
