package com.mdh.future.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 * @Author: madonghao
 * @Date: 2018/12/29 10:42
 */
public class CallableFutureTest {
    public static void main(String[] args) {
        ExecutorService executor = newCachedThreadPool();
        Task task1 = new Task();
        Task task2 = new Task();
        Future<Integer> result1 = executor.submit(task1);
        Future<Integer> result2 = executor.submit(task2);
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            System.out.println("task1运行结果"+result1.get());
            System.out.println("task2运行结果"+result2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }

    static class Task implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(3000);
            int  sum = 0;
            for(int i = 0; i < 100; i++){
                sum += i;
            }
            return sum;
        }
    }
}
