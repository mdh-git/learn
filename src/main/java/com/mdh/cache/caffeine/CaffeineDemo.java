package com.mdh.cache.caffeine;

import com.github.benmanes.caffeine.cache.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine的三种缓存填充策略：手动、同步加载和异步加载
 * @author madonghao
 * @create 2020-08-31 14:20
 **/
public class CaffeineDemo {
    public static void main(String[] args) {

        //manual();

        //sync();

        //async();

        //sizeOne();

        //sizeTwo();

        //timeOne();

        //timeTwo();

        //custom();

        //recovery();

        //refresh();

        statistics();
    }


    /**
     * 手动将值放入缓存之后再检索。
     *
     * get 方法可以原子方式执行计算。
     * 这意味着您只进行一次计算 — 即使多个线程同时请求该值。
     * 这就是为什么使用 get 优于 getIfPresent。
     */
    private static void manual() {
        Cache<String, DataObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        String key = "A";
        DataObject ifPresent = cache.getIfPresent(key);
        if(Objects.isNull(ifPresent)){
            System.out.println("缓存中为空");
        }
        cache.put(key, new DataObject("数据一"));
        // 移除key
        cache.invalidate(key);
        DataObject ifPresent1 = cache.getIfPresent(key);
        if(Objects.nonNull(ifPresent1)){
            System.out.println(ifPresent1.toString());
        }
        DataObject dataObject = cache.get(key, k -> DataObject.get(key));
        if(Objects.nonNull(dataObject)){
            System.out.println(dataObject);
        }
    }

    /**
     * 同步加载
     */
    private static void sync() {
        String key = "A";
        LoadingCache<Object, DataObject> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(k -> DataObject.get("Data for " + k));
        DataObject dataObject = cache.get(key);
        System.out.println(dataObject);
        Map<Object, DataObject> map = cache.getAll(Arrays.asList("A", "B", "C"));
        System.out.println(map.size());
    }

    /**
     * 异步加载
     */
    private static void async() {
        AsyncLoadingCache<Object, DataObject> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .buildAsync(k -> DataObject.get("Data for " + k));
        String key = "A";
        cache.get(key).thenAccept(dataObject -> {
            System.out.println(dataObject);
            System.out.println("key:Data for " + key + ",value:"+ dataObject.getData());
        });
    }

    /**
     * 基于大小回收
     * 假定当超过配置的缓存大小限制时会发生回收。
     * 获取大小有两种方法：缓存中计数对象，或获取权重。
     * cleanUp 方法。 因为缓存回收被异步执行，这种方法有助于等待回收的完成。
     */
    private static void sizeOne() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumSize(1)
                .build(k -> DataObject.get("Data for " + k));
        System.out.println("大小容量size：" + cache.estimatedSize());
        DataObject objectA = cache.get("A");
        System.out.println(objectA);
        System.out.println("大小容量size：" + cache.estimatedSize());
        DataObject objectB = cache.get("B");
        System.out.println(objectB);
        System.out.println("大小容量size：" + cache.estimatedSize());
        // 将第二个值添加到缓存中，这导致第一个值被删除
        // cleanUp 方法 缓存回收被异步执行，这种方法有助于等待回收的完成
        cache.cleanUp();
        System.out.println("大小容量size：" + cache.estimatedSize());
    }

    /**
     * 基于大小回收
     */
    private static void sizeTwo() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumWeight(10)
                .weigher((k,v) -> 3)
                .build(k -> DataObject.get("Data for " + k));
        System.out.println("大小容量size：" + cache.estimatedSize());
        DataObject objectA = cache.get("A");
        System.out.println(objectA);
        DataObject objectB = cache.get("B");
        System.out.println(objectB);
        DataObject objectC = cache.get("C");
        System.out.println(objectC);
        DataObject objectD = cache.get("D");
        System.out.println(objectD);
        System.out.println("大小容量size：" + cache.estimatedSize());
        cache.cleanUp();
        System.out.println("大小容量size：" + cache.estimatedSize());
    }

    /**
     * 访问后到期 — 从上次读或写发生后，条目即过期
     */
    private static void timeOne() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .build(k -> DataObject.get("Data for " + k));
        String a = cache.get("A").getData();
        System.out.println(a);
        System.out.println("大小容量size：" + cache.estimatedSize());
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.cleanUp();
        System.out.println("大小容量size：" + cache.estimatedSize());
    }

    /**
     * 写入后到期 — 从上次写入发生之后，条目即过期
     */
    private static void timeTwo() {
        LoadingCache<Object, DataObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .weakKeys()
                .weakValues()
                .build(k -> DataObject.get("Data for " + k));
        String key = "A";
        DataObject dataObject = cache.get(key);
        System.out.println(dataObject);
        System.out.println("大小容量size：" + cache.estimatedSize());
        cache.cleanUp();
        System.out.println("大小容量size：" + cache.estimatedSize());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.cleanUp();
        System.out.println("大小容量size：" + cache.estimatedSize());
    }

    /**
     * 始化自定义策略，实现 Expiry 接口
     */
    private static void custom() {
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder().expireAfter(new Expiry<String, DataObject>() {
            @Override
            public long expireAfterCreate(@NonNull String key, @NonNull DataObject dataObject, long currentTime) {
                return dataObject.getData().length() * 1000;
            }

            @Override
            public long expireAfterUpdate(@NonNull String key, @NonNull DataObject dataObject, long currentTime, @NonNegative long currentDuration) {
                return currentDuration;
            }

            @Override
            public long expireAfterRead(@NonNull String key, @NonNull DataObject dataObject, long currentTime, @NonNegative long currentDuration) {
                return currentDuration;
            }
        }).build(k -> DataObject.get("Data for " + k));
        String value = cache.get("A").getData();
        System.out.println(value);
    }

    /**
     * 基于引用回收
     * 可以将缓存配置为启用缓存键值的垃圾回收。
     * key 和 value 配置为 弱引用，并且我们可以仅配置软引用以进行垃圾回收。
     *
     * 当没有任何对对象的强引用时，使用 WeakReference 可以启用对象的垃圾收回收。
     * SoftReference 允许对象根据 JVM 的全局最近最少使用（Least-Recently-Used）的策略进行垃圾回收。
     */
    private static void recovery() {
        LoadingCache<Object, DataObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .weakKeys()
                .weakValues()
                .build(k -> DataObject.get("Data for " + k));

        Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .softValues()
                .build(k -> DataObject.get("Data for " + k));
    }

    /**
     * 刷新
     * expireAfter 和 refreshAfter 之间的区别。
     * 当请求过期条目时，执行将发生阻塞，直到 build Function 计算出新值为止。
     * 如果条目可以刷新，则缓存将返回一个旧值，并异步重新加载该值。
     */
    private static void refresh(){
        Caffeine.newBuilder()
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .build(k -> DataObject.get("Data for " + k));
    }

    /**
     * 统计
     */
    private static void statistics(){
        LoadingCache<String, DataObject> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .recordStats()
                .build(k -> DataObject.get("Data for " + k));
        cache.get("A");
        cache.get("B");
        cache.get("B");
        cache.get("C");
        cache.get("C");

        System.out.println(cache.stats().hitCount());
        System.out.println(cache.stats().missCount());
    }

}

@AllArgsConstructor
@Data
class DataObject {
    private final String data;

    private static int objectCounter = 0;
    // standard constructors/getters

    public static DataObject get(String data) {
        objectCounter++;
        return new DataObject(data);
    }
}
