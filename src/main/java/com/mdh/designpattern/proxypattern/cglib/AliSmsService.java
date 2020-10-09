package com.mdh.designpattern.proxypattern.cglib;

/**
 * @author madonghao
 * @create 2020-10-09 11:07
 **/
public class AliSmsService {

    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }

    public String get(String message) {
        System.out.println("get message:" + message);
        return message;
    }
}
