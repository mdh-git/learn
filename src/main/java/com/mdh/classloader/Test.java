package com.mdh.classloader;

/**
 * @Author: madonghao
 * @Date: 2019/2/21 16:39
 */
public class Test {
    public static void main(String[] args) {

        Object obj = new Object();
        System.out.println("Object:" + obj.getClass().getClassLoader());

        System.out.println("---------------------" );

        Test test = new Test();
        System.out.println("test:" + test.getClass().getClassLoader());

        System.out.println("---------------------" );

        //获取Test类的类加载器
        ClassLoader AppLoader = Test.class.getClassLoader();
        System.out.println(AppLoader);
        ClassLoader ExtLoader = AppLoader.getParent();
        System.out.println(ExtLoader);
        ClassLoader loader = ExtLoader.getParent();
        System.out.println(loader);
    }
}
