package com.mdh.interview.subject.jvm;

/**
 * 如何查看一个正在运行中的java程序,它的某个jvm参数是否开启？具体值是多少？
 * jdk/bin 目录下
 * jps  java的后台进程
 *  jps -l
 * jinfo 正在运行中到的java程序信息
 *  jinfo -flag PrintGCDetails 进程号
 * @author MDH
 * 2020/10/12 20:58
 */
public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("***********HelloGC");
        //Thread.sleep(Integer.MAX_VALUE);

        // 返回java虚拟机中到的内存总量
        long totalMemory = Runtime.getRuntime().totalMemory();
        // 返回java虚拟机试图使用的最大内存量
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("totalMemory(-Xms) = " + totalMemory + "字节");
        System.out.println("maxMemory(-Xmx) = " + maxMemory + "字节");

        // Java heap space
        // -Xms10m -Xmx10m -XX:+PrintGCDetails
        // byte[] bytes = new byte[50 * 1024 * 1024];
    }
}
