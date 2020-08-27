package com.mdh.interview.subject.hashSetCode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class HashSetDemo {
    public static void main(String[] args) {
        // demo1();

        //demo2();

        demo3();
    }

    private static void demo3() {
        Set<String> set = new CopyOnWriteArraySet();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    private static void demo2() {
        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    private static void demo1() {
        Set<String> set = new HashSet();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }


}
