package com.mdh.interview.subject.jvm.oom;

import java.util.Random;

/**
 * @author MDH
 * 2020/10/20 22:24
 *
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * 对象创建太多或者大对象,导致堆内存不足
 * byte[] byte = new byte[80 * 1024 * 1024]; 80MB
 * 是Error
 */
public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        String str = "mdh";

        // 设置JVM参数  -Xms10m -Xmx10m
        while (true) {
            str += str + new Random().nextInt(11111111) + new Random().nextInt(2222222);
            str.intern();
        }
    }
}
