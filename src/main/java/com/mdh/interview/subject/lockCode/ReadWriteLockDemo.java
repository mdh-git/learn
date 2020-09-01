package com.mdh.interview.subject.lockCode;

/**
 * @author MDH
 * 2020/9/2 0:25
 * 多个线程同时读一个资源类没有任何问题,所以为了满足并发量,读取共享资源应该可以同时进行
 *
 * 一个线程写共享资源,就不应该再有其他线程可以对改资源进行读和写
 *
 * 读-读共存
 * 读-写不共存
 * 写-写不共存
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {

    }
}
