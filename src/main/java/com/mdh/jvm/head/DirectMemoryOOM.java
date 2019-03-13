package com.mdh.jvm.head;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 直接内存溢出，没有dump文件
 * Exception in thread "main" java.lang.OutOfMemoryError
 * 设置VM参数  -XX:PermSize=10m  -XX:MaxPermSize=10m   -XX:+HeapDumpOnOutOfMemoryError
 *             -XX:MaxDirectMemorySize=10M
 * @Author: madonghao
 * @Date: 2019/3/13 15:33
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024* 1024 * 1024;
    public static void main(String[] args) throws Exception{
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while(true){
            //unsafe直接想操作系统申请内存
            unsafe.allocateMemory(_1MB);
        }
    }
}
