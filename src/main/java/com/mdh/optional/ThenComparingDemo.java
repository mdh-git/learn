package com.mdh.optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 复合 Lambda 表达式
 *
 * @author madonghao
 * @create 2020-08-28 15:09
 **/
public class ThenComparingDemo {

    public static void main(String[] args) {

        List<Flower> list = buildList();
        reversed(list);
        System.out.println(list);

        thenComparing(list);
        System.out.println(list);

    }

    private static void thenComparing(List<Flower> list) {
        list.sort(Comparator.comparing(Flower::getPrice).reversed().thenComparing(Flower::getColor));
    }

    /**
     * 倒序排序
     * @param list
     */
    private static void reversed(List<Flower> list) {
        list.sort(Comparator.comparing(Flower::getPrice).reversed());
    }

    private static List<Flower> buildList() {
        List<Flower> list = new ArrayList<>();
        Flower flower1 = new Flower(10, "粉红色");
        Flower flower2 = new Flower(10, "粉绿色");
        Flower flower3 = new Flower(10, "红色");
        Flower flower4 = new Flower(20, "红白色");
        Flower flower5 = new Flower(20, "黑色");
        Flower flower6 = new Flower(20, "白色");
        list.add(flower1);
        list.add(flower2);
        list.add(flower3);
        list.add(flower4);
        list.add(flower5);
        list.add(flower6);
        return list;
    }
}
