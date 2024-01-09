package com.mdh.thread.concurrent.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过加锁计数，然后循环判断。
 * 优缺点分析
 * ● 优点 ：手动维护方式更加灵活，对于一些特殊场景可以手动处理。
 * ● 缺点 ：和CountDownLatch相比，一样需要知道线程数目，但是代码实现比较麻烦。
 */
public class ShutdownThreadPoolDemoFour {

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

    private static int taskNum = 0; //计数器

    /**
     * 方法四：公共计数
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            int index = i;
            pool.execute(() -> {
                sleepMethod(index);
                lock.lock();
                taskNum++;
                lock.unlock();
            });
        }
        while(taskNum < 10) {
            Thread.sleep(1000);
            System.out.println("还没停止。。。当前完成任务数:" + taskNum);
        }
        System.out.println("全部执行完毕");
    }
}
