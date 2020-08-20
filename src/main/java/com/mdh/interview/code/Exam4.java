package com.mdh.interview.code;

import java.util.Arrays;

/**
 *
 * 方法的参数传递机制
 * (1)形参是基本数据类型
 *      传递数据值
 * (2)实参是引用数据类型
 *      传递的是地址
 *      特殊的类型:String 、包装类等对象不可变
 * @Author : mdh
 * @Date: 2019/3/17
 */
public class Exam4 {
    public static void main(String[] args) {
        int i = 1;
        String str = "hello";
        Integer num = 200;
        int[] arr = {1, 2, 3, 4, 5};
        MyData my = new MyData();

        change(i, str, num, arr, my);

        System.out.println("i=" + i);
        System.out.println("str=" + str);
        System.out.println("num=" + num);
        System.out.println("arr=" + Arrays.toString(arr));
        System.out.println("my.a=" + my.a);
    }

    private static void change(int i, String str, Integer num, int[] arr, MyData my) {
        i += i;
        str += "world";
        num += 1;
        arr[0] += 1;
        my.a += 10;

    }


}

class MyData{
    int a = 10;
}
