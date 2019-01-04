package com.mdh.future;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

/**
 *
 * @author madonghao
 * @date 2018/10/12
 */
public class FutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        useFture();
    }

    private static void useFture() throws InterruptedException, ExecutionException, TimeoutException {
        //ExecutorService executorService = Executors.newFixedThreadPool(3);
        ExecutorService executorService = newFixedThreadPool(3);
        Future<Integer> futureA = (Future<Integer>) executorService.submit(new Task("A1"));
        Future<Integer> futureB = (Future<Integer>) executorService.submit(new Task("B1"));
        System.out.println("a,b请求完成");

        int a = futureA.get(1, TimeUnit.SECONDS);
        int b = futureB.get(1, TimeUnit.SECONDS);

        executorService.shutdown();
        System.out.println("所有任务执行完毕，a = " + a);
        System.out.println("所有任务执行完毕，b = " + b);


    }
}
