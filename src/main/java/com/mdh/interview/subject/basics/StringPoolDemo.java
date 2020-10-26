package com.mdh.interview.subject.basics;

/**
 * 字符串常量Java内部加载
 * 在 深入理解Java虚拟机 这本书中提过
 * @author MDH
 * 2020/10/26 23:28
 * intern 方法
 * public native String intern();
 *
 * 问题：方法区和运行时常量池溢出
 *
 *
 * 运行时常量池是方法区的一部分,JDK8完全使用元空间代替永久代(常量池移动到堆上)
 *
 * String.intern()是一个本地方法,
 * 它的作用是如果字符串常量池中已经包含了一个等于此String对象的字符串,则返回代表池中这个字符串的String对象的引用;
 * 否则,会将此Sting对象包含的字符串添加到常量池中,并且返回此String对象的引用。
 * 在JDK6或者更早之前的HotSpot虚拟机中,常量池都是分配在永久代中,
 * 我们可以通过-XX:PermSize和-XX:MaxPerSize限制永久代的大小,即可间接限制其中常量池的容量
 *
 *
 * 下面的问题：
 * 有一个初始化的Java字符串(JDK自带的),在加载sun.misc.Version这个类的时候进入常量池。
 *
 * 在 public final class System类
 * private static void initializeSystemClass(){
 *     ...
 *     sun.misc.Version.init();
 *     ...
 * }
 *
 * 在Version类被加载的时候,
 * private static final java.lang.String launcher_name = "java";静态常量已经被加载
 *
 * 类加载器和rt.jar 已经被加载过
 *
 * 查看OpenJDK(openjdk.java.net)查看源码
 *
 * sun.misc.Version类会在JDK类库的初始化过程中被加载并初始化,而在初始化时它需要对静态常量字段根据指定的
 * 常量值(ConstantValue)做默认初始化,此时被sun.misc.Version.launcher_name静态常量字段所引用的"java"
 * 字符串字面量就被intern到HotSpot VM的字符串常量池--StirngTable里。
 */
public class StringPoolDemo {
    public static void main(String[] args) {
        String str1 = new StringBuilder("abc").append("edf").toString();
        System.out.println(str1);//abcedf
        System.out.println(str1.intern());//abcedf
        System.out.println(str1 == str1.intern());//true

        System.out.println();

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2);//java
        System.out.println(str2.intern());//java
        System.out.println(str2 == str2.intern());//false

        System.out.println();

        String str3 = new StringBuilder("Cla").append("ss").toString();
        System.out.println(str3);//Class
        System.out.println(str3.intern());//Class
        System.out.println(str3 == str3.intern());//true
    }
}
