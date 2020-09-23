package com.mdh.interview.subject.hashMapCode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapDemo {
    public static void main(String[] args) {
         demo1();

        //demo2();

        //demo3();
    }

    /**
     * ConcurrentHashMap
     */
    private static void demo3() {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    private static void demo2() {
        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    private static void demo1() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
