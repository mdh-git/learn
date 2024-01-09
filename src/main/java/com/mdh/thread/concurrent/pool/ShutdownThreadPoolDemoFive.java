package com.mdh.thread.concurrent.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 优点：使用简单，不需要关闭线程池。
 * 缺点：每个提交给线程池的任务都会关联一个Future对象，这可能会引入额外的内存开销。如果需要处理大量的任务，可能会占用较多的内存。
 */
public class ShutdownThreadPoolDemoFive {
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
     * 方法五：Future
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Future future = pool.submit(() -> sleepMethod(1));
        while (!future.isDone()){
            Thread.sleep(1000);
            System.out.println("还没停止。。。");
        }
        System.out.println("全部执行完毕");
    }
}
