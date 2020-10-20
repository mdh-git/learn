package com.mdh.interview.subject.jvm;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author MDH
 * 2020/10/20 20:17
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        //myHashMap();

        myWeakHashMap();
    }

    private static void myWeakHashMap() {
        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "weakHashMap";
        weakHashMap.put(key, value);
        System.out.println(weakHashMap);

        key = null;
        System.out.println(weakHashMap);

        System.gc();
        System.out.println(weakHashMap);
    }

    private static void myHashMap() {
        HashMap<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";

        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }
}
