package com.mdh.interview.subject.callable.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池异常处理
 * try-catch
 * @author MDH
 * @date 2023/6/22 23:36
 */
public class HandlerException implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        HandlerException task = new HandlerException();
        service.submit(task);
    }
}
