package com.mdh.jvm.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 写2个程序，分别使用无锁的方式，和有锁的方式对某一个整数进行++操作直到某一个很大的值M，
 * 同样使用N个线程，给出2个程序的性能比较
 * 当N=3 30 300 1000
 * M=1000000
 * 给出以上4种情况的性能比较
 *
 * 当n=3时:
 *   LockRunnable threads 3：startTime:1552280327188  endTime:1552280327274 ctime:86 value:2000002
 * NoLockRunnable threads 3：startTime:1552280327274  endTime:1552280327311 ctime:37 value:2000002
 *
 * 当n=30时:
 *   LockRunnable threads 30：startTime:1552280468731  endTime:1552280468809 ctime:78 value:2000029
 * NoLockRunnable threads 30：startTime:1552280468810  endTime:1552280468856 ctime:46 value:2000001
 *
 * 当n=300时:
 *   LockRunnable threads 300：startTime:1552280395814  endTime:1552280395942 ctime:128 value:2000204
 * NoLockRunnable threads 300：startTime:1552280395943  endTime:1552280396010 ctime:67 value:2000001
 *
 * 当n=1000时:
 *   LockRunnable threads 1000：startTime:1552280436511  endTime:1552280436826 ctime:315 value:2000103
 * NoLockRunnable threads 1000：startTime:1552280436827  endTime:1552280437012 ctime:185 value:2000000
 *
 * 总结如下：
 * 无锁的方式比有锁快，当M越大时，差别越大。
 *
 *    JVM获得锁的一般步骤是什么?
 * 1、偏向锁可用会先尝试偏向锁
 * 2、轻量级锁可用会先尝试轻量级锁
 * 3、以上都失败，尝试自旋锁
 * 4、再失败，尝试普通锁，使用OS互斥量在操作系统层挂起
 * @Author: madonghao
 * @Date: 2019/3/11 10:38
 */
public class JvmLockCompare {

    //线程数
    private static int nThreads = 30;
    //最大值
    private static int endValue = 2000000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("当前线程数量:" + nThreads);
        testLockMethod();
        testNoLockMethod();
    }

    /**
     * 测试没有锁的情况
     */
    private static void testLockMethod() throws InterruptedException {

        //有锁的方式
        LockRunnable lockRunnable = new LockRunnable();
        LockRunnable.endValue = endValue;

        //初始化线程
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        int size = nThreads;
        ExecutorService executorService = new ThreadPoolExecutor(size,size,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);

        //开始计算
        long start = System.currentTimeMillis();
        for(int i = 0 ; i < nThreads; i++){
            executorService.submit(lockRunnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

        //结束时间
        long end = System.currentTimeMillis();

        //打印日志
        System.out.println("  LockRunnable threads "+nThreads+"：startTime:"+ start +"  endTime:"+ end +" ctime:"+(end-start)+" value:"+LockRunnable.startValue);
    }

    /**
     * 测试无锁的情况
     */
    private static void testNoLockMethod() throws InterruptedException {

        //无锁方式
        NoLockRunnable noLockRunnable = new NoLockRunnable();
        NoLockRunnable.endValue = endValue;

        //初始化线程
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        int size = nThreads;
        ExecutorService executorService = new ThreadPoolExecutor(size,size,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);

        //开始计算
        long start = System.currentTimeMillis();
        for(int i = 0 ; i < nThreads; i++){
            executorService.submit(noLockRunnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

        //结束时间
        long end = System.currentTimeMillis();

        //打印日志
        System.out.println("NoLockRunnable threads "+ nThreads +"：startTime:"+ start +"  endTime:"+ end +" ctime:"+(end-start)+" value:"+NoLockRunnable.startValue);

    }
}

/**
 * 无锁的方式:为了保证线程的安全，使用原子操作类AtomicInteger
 */
class NoLockRunnable implements Runnable{

    protected static AtomicInteger startValue = new AtomicInteger();
    protected static int endValue;

    @Override
    public void run() {
        int value = startValue.get();

        while(value < endValue){
            value = startValue.incrementAndGet();
        }
    }
}

class LockRunnable implements Runnable{

    protected static int startValue;
    protected static int endValue;

    @Override
    public void run() {
        while (startValue < endValue){
            addValue();
        }
    }

    protected synchronized void addValue(){
        startValue++;
    }
}


