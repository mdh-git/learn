package com.mdh.designpattern.handler;

import java.text.ParseException;

public class NumberHandler extends HandlerDemo {
    @Override
    void transformStr(String str) {
        try {
            int num = Integer.parseInt(str);
            System.out.println("传入的数字字符串：" + num);
        } catch (Exception e) {
            // 数字处理无法处理，转给下一个处理类处理
            nextHandler.transformStr(str);
        }

    }
}
