package com.mdh.interview.subject.callable.exception;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author MDH
 * @date 2023/6/22 23:43
 *
 * 重写ThreadPoolExecutor.afterExecute方法，处理传递的异常引用
 */
public class HandlerException2 implements Runnable {
    @Override
    public void run() {
        int i = 10 / 0 ;
    }

    public static class MyThreadPoolExecutor extends ThreadPoolExecutor {

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if(t != null){
                System.err.println("任务执行异常：" + t.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        HandlerException2 task = new HandlerException2();
        executor.submit(task);
    }
}
