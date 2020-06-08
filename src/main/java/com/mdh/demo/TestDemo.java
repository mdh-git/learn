package com.mdh.demo;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author madonghao
 * @create 2020-05-11 9:46
 **/
public class TestDemo {
    public static void main(String[] args) {
        String[] a = new String[2];
        System.out.println("a:" + a.getClass());
        Object[] b = a;
        System.out.println("b:" + b.getClass());
        a[0] = "hi";
        b[1] = Integer.valueOf(42);
    }

    @Test
    public void test01(){
        String[] a = new String[2];
        System.out.println("a:" + a.getClass());
        Object[] b = a;
        System.out.println("b:" + b.getClass());
        a[0] = "hi";
        b[1] = Integer.valueOf(42);
    }

    @Test
    public void test02(){
        String[] a = new String[2];
        //a[1] = Integer.valueOf(42);
    }

    @Test
    public void test03(){
        int a = 42;
        double b = 42.0;
        Integer a1 = 42;
        Double b1 = 42.0;
        System.out.println(a == b);
        System.out.println(a1 == a);
    }


    @Test
    public void test04(){
        //JVM会自动维护八种基本类型的常量池，int常量池中初始化-128~127的范围，
        //所以当为Integer i=127时，在自动装箱过程中是取自常量池中的数值，而当Integer i=128时，128不在常量池范围内，所以在自动装箱过程中需new 128，所以地址不一样。
        Integer a = 127;
        Integer b = 127;
        System.out.println(a == b);

        Integer a1 = 128;
        Integer b1 = 128;
        System.out.println(a1 == b1);
    }

    @Test
    public void test05(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        for (String s : list) {
            if("2".equals(s)){
                list.remove(s);
            }
        }
        System.out.println(list);
    }

    public int test06(int a){
        try {
            a = 2;
            int i = 10 /0;
            System.out.println("1:" + a);
        }catch (Exception e){
            a = 3;
            System.out.println("2:" + a);
            return a;
        } finally {
            a = 4;
            System.out.println("3:" + a);
        }
        return a;
    }

    @Test
    public void test07(){
        final int a = 5;
        System.out.println(test06(a));
    }

    @Test
    public void test08(){
        Integer integer = new Integer(1);
        int i = 1;
        System.out.println(integer.equals(i));
        List list = new ArrayList();
        LinkedList list1 = new LinkedList();
    }

    @Test
    public void test09(){
        int i = 21;
        if(i > 20){
            System.out.println("充足");
        } else if(i > 0){
            System.out.println("稀少");
        } else {
            System.out.println("不足");
        }
    }

    @Test
    public void test10(){
        User u1 = new User();
        u1.setName("1");
        u1.setSex(0);
        User u2 = new User();
        u2.setName("2");
        u2.setSex(1);
        User u3 = new User();
        u3.setName("3");
        u3.setSex(null);
        User u4 = new User();
        u4.setName("4");
        u4.setSex(0);

        List<User> list = new ArrayList(3);
        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);

        User next = list.stream().filter(v -> null == v.getSex() || 0 == v.getSex().intValue()).iterator().next();
        System.out.println(JSON.toJSONString(next));
    }
}

