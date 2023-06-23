package com.mdh.interview.subject.callable.exception;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author MDH
 * @date 2023/6/22 23:49
 *
 * Future.get
 */
public class HandlerException3 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            throw new RuntimeException("任务执行失败");
        });

        try{
            String result = future.get();
            System.out.println(result);
        } catch (ExecutionException e){
            Throwable ex = e.getCause();
            System.out.println("捕捉到异常：" + ex.getMessage());
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            executor.shutdownNow();
            System.out.println("线程被中断，已执行相应处理：");
        }
    }
}
