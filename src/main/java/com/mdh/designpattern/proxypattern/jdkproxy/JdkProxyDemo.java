package com.mdh.designpattern.proxypattern.jdkproxy;

/**
 * @author madonghao
 * @create 2020-10-09 10:00
 **/
public class JdkProxyDemo {
    public static void main(String[] args) {
        SmsService smsService = (SmsService)JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("哈哈");
    }
}
