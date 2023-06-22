package com.mdh.threadlocal;

/**
 * ThreadLocal父子线程通信
 *
 * 重写了 ThreadLocal的getMap，返回t.inheritableThreadLocals
 * ThreadLocalMap getMap(Thread t) {
 *        return t.inheritableThreadLocals;
 * }
 */
public class InheritableThreadLocalsDemo {

    public static void main(String[] args) {

        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("threadLocal");

        InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal();
        inheritableThreadLocal.set("分享 + inheritableThreadLocal");
        
        new Thread( () -> {
            Object o = threadLocal.get();
            System.out.println("threadLocal: " + o );

            Object o1 = inheritableThreadLocal.get();
            System.out.println("inheritableThreadLocal: " + o1 );
        }).start();

    }
}
