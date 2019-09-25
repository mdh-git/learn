package com.mdh.demo;

import org.junit.Test;

/**
 * DESCRIPTION: TODO
 *
 * @author donghao.ma
 * @date 2019/9/24 14:03
 */
public class ThreadLoaclTest {

    @Test
    public void test(){
        ThreadLocal<String> threadLocal = new  ThreadLocal();
        threadLocal.set("123");
        System.out.println(threadLocal.get());
        threadLocal.remove();
        System.out.println(threadLocal.get());
    }
}
