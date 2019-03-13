package com.mdh.jvm.head;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 永久代内存溢出的情况，有dump文件
 * java.lang.OutOfMemoryError: PermGen space
 * 设置VM参数  -XX:+HeapDumpOnOutOfMemoryError -XX:PermSize=10m -XX:MaxPermSize=10m
 * @Author: madonghao
 * @Date: 2019/3/13 15:19
 */
public class PermGenSpaceOOM {
    public static void main(String[] args) {
        List list = new ArrayList();
        int i = 0;
        for(;;){
            list.add(String.valueOf(i++).intern());
        }
    }
}
