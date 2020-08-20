package com.mdh.test;

import org.junit.Test;

/**
 * @author madonghao
 * @create 2020-07-03 15:32
 **/
public class DemoTest {

    @Test
    public void test01(){
        // 如果相对应位都是 0，则结果为 0，否则为 1
        // 0011   3
        // 1001   9
        // 1011  11

        System.out.println(3|9);
    }
}
