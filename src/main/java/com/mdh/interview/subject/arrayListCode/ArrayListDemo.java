package com.mdh.interview.subject.arrayListCode;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayListDemo {
    public static void main(String[] args) {
        // 容量为10的数组
        new ArrayList<Integer>().add(1);

        // demo1();

        // demo2();

        // demo3();

        // demo4();

        demo5();
    }

    /**
     * JUC下面的CopyOnWriteArrayList 写时复制,读写分离的思想
     */
    private static void demo5() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 使用工具类Collections 里面的安全方法
     */
    private static void demo4() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 使用Vector
     */
    private static void demo3() {
        List<String> list = new Vector<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * ArrayList 多线程操作导致报错
     */
    private static void demo2() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 普通的输出
     */
    private static void demo1() {
        List<String> list = Arrays.asList("a", "b", "c");
        list.stream().forEach(System.out::println);
    }
}
