package com.mdh.thread.concurrent.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 优点 ：代码优雅，不需要对线程池进行操作。
 * 缺点 ：需要提前知道线程数量；性能较差；还需要在线程代码块内加上异常判断，否则在 countDown之前发生异常而没有处理，就会导致主线程永远阻塞在 await。
 */
public class ShutdownThreadPoolDemoThree {

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
     * 方法三：CountDownLatch
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //计数器，判断线程是否执行结束
        CountDownLatch taskLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int index = i;
            pool.execute(() -> {
                sleepMethod(index);
                taskLatch.countDown();
                System.out.println("当前计数器数量：" + taskLatch.getCount());
            });
        }
        //当前线程阻塞，等待计数器置为0
        taskLatch.await();
        System.out.println("全部执行完毕");
    }
}
