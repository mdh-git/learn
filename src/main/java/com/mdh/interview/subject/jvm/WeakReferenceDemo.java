package com.mdh.interview.subject.jvm;

import java.lang.ref.WeakReference;

/**
 * @author MDH
 * 2020/10/20 20:06
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference weakReference = new WeakReference(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());

        o1 = null;
        System.gc();
        System.out.println("====================");
        System.out.println(o1);
        System.out.println(weakReference.get());
    }
}
