package com.mdh.interview.code;

/**
 * 类的初始化过程
 * 一个类要创建实例需要先加载并初始化该类
 *      main方法所在的类需要加载和初始化
 * 一个子类需要初始化先初始化父类
 * 一个类初始化就是执行<clinit>() 方法
 *      <clinit>() 方法由静态变量显示赋值代码块和静态代码块组成
 *      类变量显示赋值代码块和静态代码块从上到下顺序执行
 *      <clinit>() 方法只执行一次
 *
 * 实例初始化
 * 实例舒适化就是执行<init>() 方法
 *      <init>()方法可能加载有多个，有几个构造器就有几个<init>方法
 *      <init>()方法由非静态实例变量显示赋值代码和非静态代码块、对应构造器代码组成
 *      非静态实例变量显示赋值代码和非静态代码块从上到下顺序执行,而对应构造器的代码最后执行
 *      每次创建对象,调用对应构造器,执行的就是对应的<init>方法
 *      <init>方法的首行是super()或super(实参列表),即对应父类的<init>方法
 * @Author : mdh
 * @Date: 2019/3/17
 *
 * 父类的初始化<clinit>
 *  (1)j = method();
 *  (2)父类的静态代码块
 */
public class Father {

    private int i = test();
    private static int j = method();

    static {
        System.out.println("Father:(1)");
    }

    Father(){
        System.out.println("Father:(2)");
    }

    {
        System.out.println("Father:(3)");
    }

    public int test() {
        System.out.println("Father:(4)");
        return 1;
    }

    public static int method() {
        System.out.println("Father:(5)");
        return 1;
    }

}
