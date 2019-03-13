package com.mdh.jvm.head;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 简单的堆内存溢出的情况
 * 设置VM参数  -XX:+HeapDumpOnOutOfMemoryError -Xmx8m -Xms8m
 * @Author: madonghao
 * @Date: 2019/3/13 10:05
 */
public class HeadOOM {

    private Byte [] bytes = new Byte[128 * 1024];

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(5000);

        List<HeadOOM> list = new ArrayList<HeadOOM>();
        for(;;){
             Thread.sleep(400);
            list.add(new HeadOOM());
        }
    }
}
