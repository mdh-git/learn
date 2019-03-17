package com.mdh.interview;

/**
 * 单例对象 占用资源多，需要延时加载，静态内部类 好于 懒汉式
 * 静态内部类
 * 在内部类被加载和初始化的时候,才创建instance
 * 静态内部类不会自动随着外部类的加载和初始化而被初始化,它是要单独加载和初始化的。
 * 因为是在内部类加载和初始化时才创建的,所以是线程安全的
 * @Author : mdh
 * @Date: 2019/3/17
 */
public class Singleton7 {

    private Singleton7(){

    }
    private static class Inner{
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    public static Singleton7 getInstance() {
        return Inner.INSTANCE;
    }
}
