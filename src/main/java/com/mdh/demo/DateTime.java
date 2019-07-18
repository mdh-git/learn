package com.mdh.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DESCRIPTION: 在年月日后面加上时分秒
 *
 * @author donghao.ma
 * @date 2019/7/1 19:44
 */
public class DateTime {
    public static void main(String[] args) throws ParseException {
        /// 2019-05-27 23:50:59
        String beginTime = "2019-05-27";
        String endTime = "2019-05-27";
        System.out.println(endTime.length() > 10);
        beginTime += " 00:00:00";
        endTime += " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = sdf.parse(beginTime);
        Date end = sdf.parse(endTime);
        System.out.println(begin);
        System.out.println(end);
    }
}
