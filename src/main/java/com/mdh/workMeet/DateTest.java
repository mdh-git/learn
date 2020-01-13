package com.mdh.workMeet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * 时间格式化 YYYY-MM-dd 和 yyyy-MM-dd的差别
 *
 * (1)当时间为2019-08-31时 calendar.set(2019, Calendar.AUGUST, 31); 结果
 *      2019-08-31 to yyyy-MM-dd: 2019-08-31
 *      2019-08-31 to YYYY-MM-dd: 2019-08-31
 *      不会出现问题
 * (2)当时间为2019-12-31时 calendar.set(2019, Calendar.DECEMBER, 31); 结果
 *      2019-12-31 to yyyy-MM-dd: 2019-12-31
 *      2019-12-31 to YYYY-MM-dd: 2020-12-31
 *      出现问题
 *
 * 原因：
 *      Y 和 y 实际上代表了不同的含义。
 *      y：year-of-era；正正经经的年；
 *      Y：week-based-year；只要本周跨年，那么这周就算入下一年；也就是 12 月
 * @author madonghao
 * @create 2020-01-13 14:25
 **/
public class DateTest {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();

        // 2019-08-31
        //calendar.set(2019, Calendar.AUGUST, 31);
        // 2019-12-31
        calendar.set(2019, Calendar.DECEMBER, 31);

        Date strDate = calendar.getTime();

        DateFormat formatUpperCase = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("2019-08-31 to yyyy-MM-dd: " + formatUpperCase.format(strDate));

        formatUpperCase = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println("2019-08-31 to YYYY-MM-dd: " + formatUpperCase.format(strDate));
    }
}
