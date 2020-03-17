package com.mdh.thread;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * thenApply上次执行的结果在执行
 *
 *
 * whenComplete 可以捕获异常
 * whenComplete((r, e) ->{
 *             System.out.println("方法执行后结果：" + r);
 *             System.out.println("方法执行后异常：" + e);
 *         })
 *
 * CompletableFuture.anyOf 任意一个线程执行完毕
 * CompletableFuture.allOf 所有线程执行完毕
 *
 * @author madonghao
 * @create 2020-03-04 19:11
 **/
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(10);
        System.out.println("主线程开始：" + Thread.currentThread());

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->{
            System.out.println("当前线程开始：" + Thread.currentThread());
            String uuid = UUID.randomUUID().toString();
            System.out.println("当前线程结束：" + Thread.currentThread());
            return uuid;
        }, pool).thenApply((r) -> {
            System.out.println("上一步执行的结果：" + r);
            return r.replace("-", "");
        }).whenComplete((r, e) ->{
            System.out.println("方法执行后结果：" + r);
            System.out.println("方法执行后异常：" + e);
        });

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() ->{
            System.out.println("当前线程开始：" + Thread.currentThread());
            String uuid = UUID.randomUUID().toString();
            System.out.println("当前线程结束：" + Thread.currentThread());
            return uuid;
        }, pool).thenApply((r) -> {
            System.out.println("上一步执行的结果：" + r);
            return r.replace("-", "");
        }).thenApply((r) ->{
            return Integer.valueOf(r);
        }).whenComplete((r, e) ->{
            System.out.println("方法执行后结果：" + r);
            System.out.println("方法执行后异常：" + e);
        });

        CompletableFuture<Object> any = CompletableFuture.anyOf(future, future1);
        CompletableFuture<Void> all = CompletableFuture.allOf(future, future1);

        // 包含的所有线程插队执行
        all.join();

        // 所有都完成
        System.out.println(all.get());

        System.out.println("主线程结束：" + Thread.currentThread());
        System.out.println(future.get());
        System.out.println(future1.get());
    }
}
