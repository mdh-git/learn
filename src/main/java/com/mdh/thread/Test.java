package com.mdh.thread;


import java.util.concurrent.*;

/**
 * @Author: madonghao
 * @Date: 2019/3/15 16:45
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        System.out.println("主线程开始...");
//        Thread01 thread01 = new Thread01();
//        new Thread(thread01).start();
//
//        new Thread(new Thread02()).start();
//
//        FutureTask<String> task = new FutureTask<>(new Thread03());
//        new Thread(task).start();
//        // 阻塞方法
//        System.out.println(task.get());
//
//        System.out.println("主线程结束...");

        ExecutorService pool = Executors.newFixedThreadPool(2);
        System.out.println("线程池任务准备..");
        for (int i = 0; i < 10; i++){
            Thread thread = new Thread(() -> {
                System.out.println("当前线程开始：" + Thread.currentThread());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e){

                }
                System.out.println("当前线程结束：" + Thread.currentThread());
            });
            pool.submit(thread);
        }
        System.out.println("线程池任务结束..");

    }
}

class Thread01 extends Thread{

    @Override
    public void run(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e){

        }
    System.out.println("Thread01 当前线程：" + Thread.currentThread());
    }

}

class Thread02 implements Runnable{

    @Override
    public void run(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e){

        }
        System.out.println("Thread02 当前线程：" + Thread.currentThread());
    }

}

class Thread03 implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("开始运行");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e){

        }
        System.out.println("运行结束");
        return "得到的结果";
    }
}