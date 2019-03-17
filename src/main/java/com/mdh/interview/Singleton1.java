package com.mdh.interview;

/**
 * 饿汉式
 *  直接创建对象,不管是否使用,对象都会创建
 *
 * (1)构造器私有化
 * (2)自行创建,并且静态变量保存
 * (3)向外提供实例
 * (4)强调这是一个单例,使用final 关键字
 * @Author : mdh
 * @Date: 2019/3/16
 */
public class Singleton1 {

    private static final Singleton1 INSTANCE = new Singleton1();
    private Singleton1(){
    }
}
