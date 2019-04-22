package com.mdh.thread.concurrent.CallableTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author : mdh
 * @Date: 2019/3/23
 */
public class CallableTest01 implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "hello";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new CallableTest01());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
