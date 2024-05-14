package com.mdh.cv;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DemoTest {

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        DemoD1 d1 = DemoD1.builder().objId("D1").wide("100").length("200").height("300").build();
        DemoC1 c1 = DemoC1.builder().objId("C1").demoD1(d1).build();
        DemoB1 b1 = DemoB1.builder().objId("B1").demoC1(c1).build();
        DemoA a = DemoA.builder().objId("A").demoB1(b1).build();

//        Class<?> aClass = Class.forName("com.mdh.cv.DemoA");
//
//        Field[] declaredFields = aClass.getDeclaredFields();
//
//        Field field = aClass.getDeclaredField("demoB1");
//        Class<?> type = field.getType();
//
//        if (type == List.class){
//            List list = (List)field.get(a);
//
//            for (Object o : list) {
//
//            }
//        }


        Class<?> aClass = a.getClass();
//        Object demoB1 = getAttribute1(a, aClass, "demoB1");
//        System.out.println(demoB1);

        List<String> list = new ArrayList<>();
        list.add("demoB1");
        list.add("demoC1");
        list.add("demoD1");
        list.add("height");

        Object object = getObjValue(a, aClass, list);
        System.out.println(object);
    }

    private static Object getObjValue(Object a, Class<?> aClass, List<String> list) {
        final Class<?>[] type = {aClass};
        final Object[] object = {a};
        list.forEach(v -> {
            Field field = null;
            try {
                field = type[0].getDeclaredField(v);
                field.setAccessible(true);
                type[0] = field.getType();
                object[0] = field.get(object[0]);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }  catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return object[0];
    }

    private static Object getAttribute1(DemoA a, Class<?> aClass, String demoB1) throws NoSuchFieldException, IllegalAccessException {
        Field field = aClass.getDeclaredField(demoB1);
        field.setAccessible(true);
        return field.get(a);
    }

    private static Object getAttribute(DemoA a, Class<?> b1Class) throws NoSuchFieldException, IllegalAccessException {

        Class<? extends DemoA> aClass1 = a.getClass();

        Field demoB11 = aClass1.getDeclaredField("demoB1");

        demoB11.setAccessible(true);
        return demoB11.get(a);

    }

}
