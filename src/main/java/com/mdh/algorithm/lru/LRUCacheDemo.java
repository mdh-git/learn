package com.mdh.algorithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author madonghao
 * @create 2020-11-09 14:25
 **/
public class LRUCacheDemo<K, V> extends LinkedHashMap<K, V> {

    /**
     * 缓存大小
     */
    private int capacity;

    public LRUCacheDemo(int capacity){
        super(capacity, 0.75F, false);
        this.capacity = capacity;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K,V> eldest){
        return super.size() > capacity;
    }

    public static void main(String[] args) {
        LRUCacheDemo cache = new LRUCacheDemo(3);

        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        System.out.println(cache.keySet());

        cache.put(4, "d");
        System.out.println(cache.keySet());

        cache.put(3, "c");
        System.out.println(cache.keySet());
        cache.put(3, "c");
        System.out.println(cache.keySet());
        cache.put(3, "c");
        System.out.println(cache.keySet());
        cache.put(5, "x");
        System.out.println(cache.keySet());
        cache.put(6, "y");
        System.out.println(cache.keySet());
    }
}
