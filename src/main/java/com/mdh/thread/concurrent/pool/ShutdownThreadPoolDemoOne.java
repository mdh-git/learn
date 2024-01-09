package com.mdh.thread.concurrent.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在主线程中进行循环判断，全部任务是否已经完成。
 * 这里有两个主要方法：
 * ● shutdown() ：对线程池进行有序关闭。调用该方法后，线程池将不再接受新的任务，但会继续执行已提交的任务。如果线程池已经处于关闭状态，则对该方法的调用没有额外的作用。
 * ● isTerminated() ：判断线程池中的所有任务是否在关闭后完成。只有在调用了shutdown()或shutdownNow()方法后，所有任务执行完毕，才会返回true。需要注意的是，在调用shutdown()之前调用isTerminated()方法始终返回false。
 * 优缺点分析
 * 优点 ：操作简单。
 * 缺点 ：需要关闭线程池。并且日常使用是将线程池注入到Spring容器，然后各个组件中统一用同一个线程池，不能直接关闭线程池。
 */
public class ShutdownThreadPoolDemoOne {

    /**
     * 创建一个最大线程数15的线程池
     */
    public static ThreadPoolExecutor pool = new ThreadPoolExecutor(
            10,
            15,
            0L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(10));
    /**
     * 线程执行方法，随机等待0到10秒
     */
    private static void sleepMethod(int index){
        try {
            long sleepTime = new Double(Math.random() * 10000).longValue();
            Thread.sleep(sleepTime);
            System.out.println("当前线程执行结束: " + index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法一：isTerminated
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int index = i;
            pool.execute(() -> sleepMethod(index));
        }
        pool.shutdown();
        while (!pool.isTerminated()){
            Thread.sleep(1000);
            System.out.println("还没停止。。。");
        }
        System.out.println("全部执行完毕");
    }
}
