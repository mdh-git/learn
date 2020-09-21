package com.mdh.interview.subject.callable;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author MDH
 * 2020/9/20 23:34
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {

        // getCpuInfo();

        // fixed();

        // single();

        // cached();


        //初始化线程
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(3);
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();
        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                workQueue,
                Executors.defaultThreadFactory(),
                discardOldestPolicy
        );

        // i分别尝试 1  3  5  8  9看结果
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "  处理");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    /**
     * 一池多个处理线程 可缓存的
     */
    private static void cached() {
        // 一池多个处理线程
        ExecutorService threadPool = Executors.newCachedThreadPool();
        // 模拟10个用户来办理业务,每个用户就是一个来自外部的请求线程
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "处理");
                });
                TimeUnit.MILLISECONDS.sleep(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    /**
     * 一池1个处理线程
     */
    private static void single() {
        // 一池1个处理线程
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        // 模拟10个用户来办理业务,每个用户就是一个来自外部的请求线程
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "处理");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    /**
     * 一池5个处理线程
     */
    private static void fixed() {
        // 一池5个处理线程
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        // 模拟10个用户来办理业务,每个用户就是一个来自外部的请求线程
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "处理");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void getCpuInfo() {
        // 查看CPU信息
        System.out.println(Runtime.getRuntime().availableProcessors());
    }


}
