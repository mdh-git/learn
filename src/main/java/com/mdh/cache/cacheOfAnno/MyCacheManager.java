package com.mdh.cache.cacheOfAnno;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 然后定义一个缓存管理器，这个管理器负责实现缓存逻辑，
 * 支持对象的增加、修改和删除，支持值对象的泛型。
 * @author madonghao
 * @date 2018/11/6
 */
public class MyCacheManager<T> {

    private Map<String, T> cache = new ConcurrentHashMap<String, T>();

    /** 获取key对应的值 */
    public T getValue(Object key) {
        return cache.get(key);
    }

    /** 新增 */
    public void addOrUpdateCache(String key, T value) {
        cache.put(key, value);
    }

    /** 根据 key 来删除缓存中的一条记录 */
    public void evictCache(String key) {
        if(cache.containsKey(key)){
            cache.remove(key);
        }
    }

    /** 清空缓存中的所有记录 */
    public void evictCache() {
        cache.clear();
    }

    /** 输入全部 */
    public void all() {
        System.out.println(cache);
    }

}
