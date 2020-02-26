package com.mdh.lock;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS相关的操作
 *
 * @author madonghao
 * @create 2020-02-24 9:46
 **/
public class CASTest {

    AtomicInteger atomicInteger = new AtomicInteger(1);

    @Test
    public void solutionOne() {


        atomicInteger.compareAndSet(2, 3);
        int result = atomicInteger.get();

        System.out.println(result);

        // 自增 CAS算法可以解决   会出现ABA问题
        int i = atomicInteger.incrementAndGet();
        System.out.println(i);
    }
}
