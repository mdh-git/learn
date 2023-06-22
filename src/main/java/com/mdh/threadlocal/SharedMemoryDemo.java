package com.mdh.threadlocal;

import java.util.ArrayList;

/**
 * 线程通信： 共享变量
 * @author MDH
 * @date 2023/6/22 13:28
 */
public class SharedMemoryDemo {

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (integers) {
                    integers.add(i);
                    System.out.println(Thread.currentThread().getName() + "_Producer:" + i);
                }
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }, "ProducerThread");

        Thread consumeThread = new Thread(() -> {
            while (true){
                synchronized (integers) {
                    if(!integers.isEmpty()){
                        Integer remove = integers.remove(0);
                        System.out.println(Thread.currentThread().getName() + "_Consume:" + remove);
                    }
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }, "ConsumeThread");

        producerThread.start();
        consumeThread.start();

    }
}
