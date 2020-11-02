package com.mdh.spring.circulardepend.construtorinjection;

/**
 * @author MDH
 * 2020/11/2 21:08
 */
public class ConstructorTest {
    public static void main(String[] args) {
        // 构造器循环依赖是无法解决的
        //new ServiceA(new ServiceB(new ServiceA()));
    }
}
