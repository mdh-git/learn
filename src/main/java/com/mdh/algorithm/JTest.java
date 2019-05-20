package com.mdh.algorithm;

import com.alibaba.fastjson.JSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESCRIPTION: TODO
 *
 * @author donghao.ma
 * @date 2019/5/20 9:58
 */
public class JTest {
    public static void main(String[] args) throws ParseException {
        String[] array = {
                "2012/9/21 6:45",
                "2012/9/21 6:45",
                "2012/9/21 6:45",
                "2012/9/21 6:50",
                "2012/9/21 6:51",
                "2012/9/21 6:52",
                "2012/9/21 6:53",
                "2012/9/21 6:55"};
        List<Date> list = new ArrayList<>(array.length);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        for(String time : array){
            list.add(sdf.parse(time));
        }
        long currentTime = 60 * 60 * 1000;
        Date date = list.stream().filter(x -> x.getTime() > list.get(1).getTime()).findFirst()
                .orElse(null);
        System.out.println(JSON.toJSONString(date));
    }
}
