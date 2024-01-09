package com.mdh.thread.concurrent.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在主线程循环判断，主要就两个方法：
 * ● getTaskCount() ：返回计划执行的任务总数。由于任务和线程的状态可能在计算过程中动态变化，返回的值只是一个近似值。这个方法返回的是线程池提交的任务总数，包括已经完成和正在执行中的任务。
 * ● getCompletedTaskCount() ：返回已经完成执行的任务的大致总数。由于任务和线程的状态可能在计算过程中动态改变，返回的值只是一个近似值，并且在连续的调用中不会减少。这个方法返回的是已经完成执行的任务数量，不包括正在执行中的任务。
 * 优缺点分析
 * ● 优点 ：不必关闭线程池，避免了创建和销毁带来的损耗。
 * ● 缺点 ：使用这种判断存在很大的限制条件；必须确定在循环判断过程中没有新的任务产生。
 */
public class ShutdownThreadPoolDemoTwo {

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
     * 方法二：getCompletedTaskCount
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int index = i;
            pool.execute(() -> sleepMethod(index));
        }
        //当线程池完成的线程数等于线程池中的总线程数
        while (!(pool.getTaskCount() == pool.getCompletedTaskCount())) {
            System.out.println("任务总数:" + pool.getTaskCount() + "； 已经完成任务数:" + pool.getCompletedTaskCount());
            Thread.sleep(1000);
            System.out.println("还没停止。。。");
        }
        System.out.println("全部执行完毕");
    }
}
