package com.mdh.future.futuretask;

import java.util.concurrent.*;

/**
 * @Author: madonghao
 * @Date: 2018/12/29 11:08
 */
public class CallableFutureTaskTest {
    public static void main(String[] args) {
        //第一种方式
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
        executor.submit(futureTask);
        executor.shutdown();

        //第二种方式，注意这种方式和第一种方式效果是类似的，只不过一个使用的是ExecutorService，一个使用的是Thread
        Task task1 = new Task();
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(task1);
        Thread thread = new Thread(futureTask1);
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            System.out.println("task 运行结果"+futureTask.get());
            System.out.println("task1运行结果"+futureTask1.get());
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
            int sum = 0;
            for(int i = 0; i < 100; i++){
                sum += i;
            }
            return sum;
        }
    }
}
