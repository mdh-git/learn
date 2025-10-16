package com.mdh.interview.subject.callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class InheritableThreadLocalDemo {

    private static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        inheritableThreadLocal.set("parent-value");

        AtomicReference<String> value = new AtomicReference<>("1231");
        // 子线程可以获取父线程值
        new Thread(() -> {
            value.set("2131321");
            System.out.println("子线程获取值: " + inheritableThreadLocal.get()); // 输出: parent-value
        }).start();

        System.out.println(value.get());

        // 但线程池场景下有问题
        ExecutorService executor = Executors.newFixedThreadPool(1);
        inheritableThreadLocal.set("task1-value");
        executor.submit(() -> {
            System.out.println("任务1: " + inheritableThreadLocal.get()); // 输出: task1-value
        });

        inheritableThreadLocal.set("task2-value");
        executor.submit(() -> {
            // 可能还是输出: task1-value，因为线程复用
            System.out.println("任务2: " + inheritableThreadLocal.get());
        });
    }
}
