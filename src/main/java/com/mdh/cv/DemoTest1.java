package com.mdh.cv;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;

public class DemoTest1 {

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, IOException {

        DemoD1 d1 = DemoD1.builder().objId("D1").wide("100").length("200").height("300").build();
        DemoC1 c1 = DemoC1.builder().objId("C1").demoD1(d1).build();
        DemoB1 b1 = DemoB1.builder().objId("B1").demoC1(c1).build();
        DemoA a = DemoA.builder().objId("A").demoB1(b1).build();

        Class<?> aClass = a.getClass();
        Field fieldA = aClass.getDeclaredField("demoB1");
        fieldA.setAccessible(true);
        Class<?> type = fieldA.getType();
        Object o = fieldA.get(a);

        Field fieldC1 = type.getDeclaredField("demoC1");
        type = fieldC1.getType();
        fieldC1.setAccessible(true);
        Object oC1 = fieldC1.get(o);

        Field fieldD1 = type.getDeclaredField("demoD1");
        type = fieldD1.getType();
        fieldD1.setAccessible(true);
        Object oD1 = fieldD1.get(oC1);

        Field height = type.getDeclaredField("height");
        height.setAccessible(true);
        Object hei = height.get(oD1);
        System.out.println(hei);



        //DemoB1 oB1 = (DemoB1)fieldA.get(a);

//        Class<?> b1Class = Class.forName("com.mdh.cv.DemoB1");
//        Field fieldC1 = b1Class.getDeclaredField("demoC1");
//        fieldC1.setAccessible(true);
//        DemoC1 oC1 = (DemoC1)fieldC1.get(oB1);

//        Class<?> c1Class = Class.forName("com.mdh.cv.DemoC1");
//        Field fieldD1 = c1Class.getDeclaredField("demoD1");
//        fieldD1.setAccessible(true);
//        DemoD1 oD1 = (DemoD1)fieldD1.get(oC1);
//
//        Class<?> d1Class = Class.forName("com.mdh.cv.DemoD1");
//        Field field = d1Class.getDeclaredField("height");
//        field.setAccessible(true);
//        String height = (String)field.get(oD1);

//        System.out.println(height);
    }
}
