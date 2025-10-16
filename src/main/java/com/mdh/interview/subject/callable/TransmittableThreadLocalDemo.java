package com.mdh.interview.subject.callable;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransmittableThreadLocalDemo {
    private static TransmittableThreadLocal<String> transmittableThreadLocal =
            new TransmittableThreadLocal<>();

    public static void main(String[] args) {
        // 包装线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Executor ttlExecutor = TtlExecutors.getTtlExecutor(executor);

        // 任务1
        transmittableThreadLocal.set("任务1的数据");
        ttlExecutor.execute(() -> {
            System.out.println("任务1: " + transmittableThreadLocal.get()); // 输出: 任务1的数据
        });

        // 任务2
        transmittableThreadLocal.set("任务2的数据");
        ttlExecutor.execute(() -> {
            System.out.println("任务2: " + transmittableThreadLocal.get()); // 输出: 任务2的数据
        });

        executor.shutdown();
    }
}
