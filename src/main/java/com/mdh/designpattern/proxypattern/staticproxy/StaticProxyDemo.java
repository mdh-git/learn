package com.mdh.designpattern.proxypattern.staticproxy;

/**
 * 静态代理
 * @author madonghao
 * @create 2020-10-09 9:29
 **/
public class StaticProxyDemo {
    public static void main(String[] args) {
        SmsService smsService = new SmsServiceImpl();
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("java");
    }
}
