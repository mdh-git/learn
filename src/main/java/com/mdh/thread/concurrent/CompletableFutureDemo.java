package com.mdh.thread.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CompletableFutureDemo {

    public final static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String str = "hello";
        Integer num = 24;

        try{
            CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> buildStr(str)).exceptionally( e -> {
                log.error("1 报错了");
                return str;
            });
            CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> buildInteger(num)).exceptionally( e -> {
                log.error("2 报错了");
                return num;
            });
            CompletableFuture.allOf(future1, future2).thenRun(() -> buildResult(future1, future2));
        } catch (Exception e){
            log.error("错误");
        } finally {
            log.info("结束");
        }



//        CompletableFuture<String> cf = CompletableFuture.supplyAsync(()-> "Hello World!");
//        System.out.println(cf.get());

    }

    private static void buildResult(CompletableFuture<String> future1, CompletableFuture<Integer> future2) {
        log.info("合并开始");
        String join1 = future1.join();
        Integer join2 = future2.join();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("合并错误");
            throw new RuntimeException(e);
        } finally {
            log.error(join1 + " " + join2);
        }
    }

    private static String buildStr(String str) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return str + "world";
    }

    private static Integer buildInteger(Integer num) {
        try {
            int i = 10 / 0;
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("2 报错了");
            return num;
        }
        return 2000 + num;
    }
}
