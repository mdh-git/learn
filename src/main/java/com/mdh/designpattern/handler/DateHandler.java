package com.mdh.designpattern.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateHandler extends HandlerDemo {

    @Override
    void transformStr(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            simpleDateFormat.parse(str);
            System.out.println("传入的为日期类型字符串：" + str);
        } catch (ParseException e) {
            // 日期处理类无法处理，转给下一个处理类处理
            nextHandler.transformStr(str);
        }
    }
}
