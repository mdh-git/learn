package com.mdh.interview.subject.callable;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TtlDemo {
    // 1. 使用 TransmittableThreadLocal
    private static final TransmittableThreadLocal<String> TTL = new TransmittableThreadLocal<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        // 首先让池中的线程先初始化，并设置自己的上下文
        executor.execute(() -> {
            TTL.set("Worker Thread's Original Context");
            System.out.println("ThreadPool Worker init: " + TTL.get());
        });
        Thread.sleep(500); // 等待初始化任务完成

        // 父线程设置自己的上下文
        TTL.set("Hello from Main Thread");

        Runnable task = () -> {
            // 任务中能正确获取到提交任务的父线程的上下文
            System.out.println("Task in ThreadPool: " + TTL.get());
        };

        // 2. 关键：使用 TtlRunnable 包装任务
        Runnable ttlTask = TtlRunnable.get(task);

        // 提交被包装后的任务
        executor.execute(ttlTask);
        // 输出：Task in ThreadPool: Hello from Main Thread

        // 再提交一个任务，查看线程池线程的上下文是否被恢复
        executor.execute(() -> {
            System.out.println("See if worker thread context restored: " + TTL.get());
            // 输出：See if worker thread context restored: Worker Thread's Original Context
        });

        executor.shutdown();
    }
}
