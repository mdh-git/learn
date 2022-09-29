package com.mdh.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Author: madonghao
 * @Date: 2022/9/8 5:45 下午
 */
public class TimeTest {

  public static void main(String[] args) {
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  Calendar calendar = Calendar.getInstance();
	  // 一周第一天为周日，所以此处日+1
	  calendar.setWeekDate(calendar.getWeekYear(), calendar.get(Calendar.WEEK_OF_YEAR), 2);
	  calendar.set(calendar.get(Calendar.YEAR),
			  calendar.get(Calendar.MONTH),
			  calendar.get(Calendar.DAY_OF_MONTH),
			  0, 0, 0);
	  System.out.println(sdf.format(calendar.getTime()));
	  // 一周第一天为周日，所以此处为下一周第一天
	  calendar.setWeekDate(calendar.getWeekYear(), calendar.get(Calendar.WEEK_OF_YEAR)+1, 1);
	  calendar.set(calendar.get(Calendar.YEAR),
			  calendar.get(Calendar.MONTH),
			  calendar.get(Calendar.DAY_OF_MONTH),
			  23, 59, 59);
	  System.out.println(sdf.format(calendar.getTime()));
  }
}
