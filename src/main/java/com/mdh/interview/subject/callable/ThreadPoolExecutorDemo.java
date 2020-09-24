package com.mdh.interview.subject.callable;

import java.util.concurrent.*;

/**
 * @author madonghao
 * @create 2020-09-24 15:09
 * ThreadPoolExecutor扩展主要是围绕
 * beforeExecute()、afterExecute()和terminated()三个接口实现的
 **/
public class ThreadPoolExecutorDemo {

    private static ExecutorService pool;

    public static void main(String[] args) {

        pool = new ThreadPoolExecutor(2,
                4,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5),
                r -> {
                    System.out.println("线程"+r.hashCode()+"创建");
                    //线程命名
                    Thread th = new Thread(r,"threadPool"+r.hashCode());
                    return th;
                },
                new ThreadPoolExecutor.CallerRunsPolicy()){

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行："+ ((ThreadTask)r).getTaskName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完毕："+((ThreadTask)r).getTaskName());
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };
        for (int i = 0; i < 10; i++) {
            pool.execute(new ThreadTask("Task" + i));

        }
        pool.shutdown();
    }
}
