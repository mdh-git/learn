package com.mdh.interview.subject.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author MDH
 * 2020/9/19 16:30
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // FutureTask(Callable<V> callable)
        FutureTask<Integer> task = new FutureTask<>(new MyThread2());
        new Thread(task, "AAA").start();
        new Thread(task, "BBB").start();

//        while (!task.isDone()){
//
//        }
        System.out.println(Thread.currentThread().getName() + "\t" + task.get());
    }
}

class MyThread implements Runnable{

    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "  come in Callable");
        return 1024;
    }
}
