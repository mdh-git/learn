package com.mdh.interview.code;

/**
 * @Author : mdh
 * @Date: 2019/3/17
 */
public class Singleton5 {
    private static Singleton5 instance;
    private Singleton5(){
    }
    public static Singleton5 getInstance(){
        if(instance == null){
            synchronized (Singleton5.class){
                if(instance == null){
                    instance = new Singleton5();
                }
            }
        }
        return instance;
    }
}
