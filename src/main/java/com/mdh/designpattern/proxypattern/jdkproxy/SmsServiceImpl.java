package com.mdh.designpattern.proxypattern.jdkproxy;

/**
 * 2.实现发送短信的接口
 * @author madonghao
 * @create 2020-10-09 9:49
 **/
public class SmsServiceImpl implements SmsService {

    @Override
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
