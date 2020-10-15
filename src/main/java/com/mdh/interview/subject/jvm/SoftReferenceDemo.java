package com.mdh.interview.subject.jvm;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * @author MDH
 * 2020/10/15 23:45
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {

        softRef_Memory_Enough();

        softRef_Memory_NotEnough();
    }

    /**
     * 内存不足
     * 设置JVM参数,故意产生大对象并配置小的内存,让它的内存不够用导致OOM,看弱引用的回收情况
     * -Xms5m -Xmx5m -XX:PrintGCDetails
     */
    private static void softRef_Memory_NotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Throwable e){
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(softReference);
        }
    }

    /**
     * 内存充足
     */
    private static void softRef_Memory_Enough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(softReference);

        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(softReference);
    }


}
