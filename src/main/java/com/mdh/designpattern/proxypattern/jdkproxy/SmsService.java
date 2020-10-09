package com.mdh.designpattern.proxypattern.jdkproxy;

/**
 * 1.定义发送短信的接口
 * @author madonghao
 * @create 2020-10-09 9:48
 **/
public interface SmsService {

    String send(String message);
}
