package com.mdh.interview.subject.volatileCode;

public class SingletonDemo {

    private static volatile SingletonDemo singleton = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "\t 构造方法");
    }

    // DCL (Double check lock 双端检锁机制)
    public static SingletonDemo getInstance(){
        if(singleton == null){
            synchronized (SingletonDemo.class){
                if(singleton == null){
                    singleton = new SingletonDemo();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
