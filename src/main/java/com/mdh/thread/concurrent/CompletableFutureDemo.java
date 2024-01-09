package com.mdh.thread.concurrent;

import java.util.concurrent.*;

public class CompletableFutureDemo {

    public final static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String str = "hello";
        Integer num = 24;

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> buildStr(str), threadPool);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> buildInteger(num), threadPool);

        CompletableFuture.allOf(future1, future2).thenRun(() -> buildResult(future1, future2));


//        CompletableFuture<String> cf = CompletableFuture.supplyAsync(()-> "Hello World!");
//        System.out.println(cf.get());
    }

    private static void buildResult(CompletableFuture<String> future1, CompletableFuture<Integer> future2) {
        String join1 = future1.join();
        Integer join2 = future2.join();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(join1 + " " + join2);
    }

    private static String buildStr(String str) {
        return str + "world";
    }

    private static Integer buildInteger(Integer num) {
        return 2000 + num;
    }
}
