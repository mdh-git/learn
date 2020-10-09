package com.mdh.designpattern.proxypattern.cglib;

/**
 * @author madonghao
 * @create 2020-10-09 13:16
 **/
public class CglibProxyDemo {
    public static void main(String[] args) {
        AliSmsService aliSmsService = (AliSmsService)CglibProxyFactory.getProxy(AliSmsService.class);
        aliSmsService.send("java");
        aliSmsService.get("哈哈");
    }
}
