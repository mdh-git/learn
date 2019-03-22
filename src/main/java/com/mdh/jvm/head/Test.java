package com.mdh.jvm.head;

/**
 * @Author: madonghao
 * @Date: 2019/3/20 18:11
 */
public class Test {
    public static void main(String[] args) {
        String str  = "abc";
        str  = "abc2";
        System.out.println(str);
        String v=new String("abc");
        String abc = v.intern();
        String v2=new String("abc");

        System.out.println(str == v);
        //System.out.println(str == str1);
        System.out.println(str == abc);
        Integer a = 10;
        Integer b = 10;
        Integer c = 200;
        Integer d = 200;
        System.out.println(a==b);
        System.out.println(c==d);

    }
}
