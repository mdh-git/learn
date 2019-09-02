package com.mdh.optional;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

/**
 * DESCRIPTION: parallelStream 测试
 *
 * @author donghao.ma
 * @date 2019/9/2 11:28
 * 参考: https://www.jianshu.com/p/17d432f211f4
 *       https://blog.csdn.net/darrensty/article/details/79283146
 */
public class ParallelStreamTest {

    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        out.print("stream: 操作结果");
        integerList.stream().forEach(out:: print);
        out.println("------------------");
        out.print("parallelStream: 操作结果");
        integerList.parallelStream().forEach(out::print);
        out.println(" ");
        out.print("parallelStream: 顺序执行 forEachOrdered ===");
        integerList.parallelStream().forEachOrdered(out::print);

        out.println("------------------");
        out.println("操作不安全的对象array");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Calendar> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Calendar startDay = new GregorianCalendar();
            Calendar checkDay = new GregorianCalendar();
            checkDay.setTime(startDay.getTime());
            checkDay.add(Calendar.DATE,i);
            list.add(checkDay);
            checkDay = null;
            startDay = null;
        }

        list.stream().forEach(day ->  System.out.println(sdf.format(day.getTime())));
        out.println("-----------------------");
        list.parallelStream().forEach(day ->  System.out.println(sdf.format(day.getTime())));
        out.println("*******************");
        list.parallelStream().forEachOrdered(day ->  System.out.println(sdf.format(day.getTime())));
        out.println("-----------------------");
    }
}
