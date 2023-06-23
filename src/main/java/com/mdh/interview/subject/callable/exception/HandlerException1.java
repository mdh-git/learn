package com.mdh.interview.subject.callable.exception;

/**
 *
 * @author MDH
 * @date 2023/6/22 23:40
 *
 * 实例化时，传入自己的ThreadFactory，设置 Thread.UncaughtExceptionHandler处理未检测的异常
 */
public class HandlerException1 implements Runnable {
    @Override
    public void run() {
        int i = 10 / 0;
    }
    
    public static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println("任务执行异常：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        HandlerException1 task = new HandlerException1();
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        thread.start();
    }
}
