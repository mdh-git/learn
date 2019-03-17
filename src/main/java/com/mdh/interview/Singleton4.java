package com.mdh.interview;

/**
 * 在单线程的情况下没有问题
 *
 * @Author : mdh
 * @Date: 2019/3/17
 */
public class Singleton4 {
    private static Singleton4 instance;
    private Singleton4(){

    }

    public static Singleton4 getInstance(){
        if (instance == null){
            instance = new Singleton4();
        }
        return instance;
    }
}
