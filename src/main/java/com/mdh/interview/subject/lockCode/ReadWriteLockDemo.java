package com.mdh.interview.subject.lockCode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author MDH
 * 2020/9/2 0:25
 * 多个线程同时读一个资源类没有任何问题,所以为了满足并发量,读取共享资源应该可以同时进行
 *
 * 一个线程写共享资源,就不应该再有其他线程可以对改资源进行读和写
 *
 *      读-读共存
 *      读-写不共存
 *      写-写不共存
 *
 *      写操作: 原子 + 独占,整个过程必须是一个完成的统一体,中间不能被打断
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {

        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() ->{
                myCache.put(finalI + "", finalI + "");
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() ->{
                myCache.get(finalI + "");
            },String.valueOf(i)).start();
        }
    }
}

/**
 * 资源类
 */
class  MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    // 不满足业务的需求
    private Lock lock = new ReentrantLock();

    // 可重入的读写锁
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value){
        try {
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t 正在写入:" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 正在写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        try {
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private void empty(){

    }
}
