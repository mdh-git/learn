package com.mdh.designpattern.proxypattern.staticproxy;

/**
 * @author madonghao
 * @create 2020-10-09 9:30
 **/
public class SmsServiceImpl implements SmsService {

    @Override
    public String send(String message) {
        System.out.println("send message：" + message);
        return message;
    }
}
