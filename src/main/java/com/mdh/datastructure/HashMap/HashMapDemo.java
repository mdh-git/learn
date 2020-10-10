package com.mdh.datastructure.HashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HashMap遍历方法
 *
 * @author madonghao
 * @create 2020-10-10 10:57
 **/
public class HashMapDemo {
    public static void main(String[] args) {
        // 创建并赋值 HashMap
        Map<Integer, String> map = new HashMap();
        map.put(1, "Java ");
        map.put(2, "JDK ");
        map.put(3, "Spring-Framework ");
        map.put(4, "MyBatis-framework ");
        map.put(5, "Java中文社群 ");

        // 遍历
        entrySet(map);
        System.out.println();

        keySet(map);
        System.out.println();

        forEachEntrySet(map);
        System.out.println();

        forEachKeySet(map);
        System.out.println();

        lambda(map);
        System.out.println();

        streamApi(map);
        System.out.println();

        parallelStreamApi(map);
    }



    /**
     * 1.迭代器 EntrySet
     * @param map
     */
    private static void entrySet(Map<Integer, String> map) {
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.print(entry.getKey());
            System.out.print(entry.getValue());
        }
    }

    /**
     * 2.迭代器 KeySet
     * @param map
     */
    private static void keySet(Map<Integer, String> map) {
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.print(next);
            System.out.print(map.get(next));
        }
    }

    /**
     * 3.ForEach EntrySet
     * @param map
     */
    private static void forEachEntrySet(Map<Integer, String> map) {
        for(Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.print(entry.getKey());
            System.out.print(entry.getValue());
        }
    }

    /**
     * 4.ForEach KeySet
     * @param map
     */
    private static void forEachKeySet(Map<Integer, String> map) {
        for(Integer key : map.keySet()) {
            System.out.print(key);
            System.out.print(map.get(key));
        }
    }

    /**
     * 5.Lambda
     * @param map
     */
    private static void lambda(Map<Integer, String> map) {
        map.forEach((key, value) -> {
            System.out.print(key);
            System.out.print(value);
        });
    }

    /**
     * 6.Streams API 单线程
     * @param map
     */
    private static void streamApi(Map<Integer, String> map) {
        map.entrySet().stream().forEach(entry -> {
            System.out.print(entry.getKey());
            System.out.print(entry.getValue());
        });
    }

    /**
     * 7.Streams API 多线程
     * @param map
     */
    private static void parallelStreamApi(Map<Integer, String> map) {
        map.entrySet().parallelStream().forEach(entry -> {
            System.out.print(entry.getKey());
            System.out.print(entry.getValue());
        });
    }
}
