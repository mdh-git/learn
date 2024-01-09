package com.mdh.designpattern.handler;

public class StringHandler extends HandlerDemo {
    @Override
    void transformStr(String str) {
        System.out.println("传入的为字符串：" + str);
    }
}
