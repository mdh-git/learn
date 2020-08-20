package com.mdh.interview.code;

/**
 *
 * 懒汉式 双重锁
 * Double CheckLock实现单例：DCL也就是双重锁判断机制（由于JVM底层模型原因，偶尔会出问题，不建议使用）
 * @Author : mdh
 * @Date: 2019/3/17
 */
public class Singleton6 {
    private volatile static Singleton6 instance;
    private Singleton6(){

    }
    public static Singleton6 getInstance() {
        if(instance == null){
            synchronized (Singleton6.class){
                if(instance == null){
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }
}
