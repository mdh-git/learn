package com.mdh.interview.subject.jvm;

/**
 * 强引用
 *
 * @author MDH
 * 2020/10/15 23:42
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = obj1;
        obj1 = null;
        System.gc();
        System.out.println(obj2);
    }
}
