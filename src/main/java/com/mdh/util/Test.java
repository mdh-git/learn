package com.mdh.util;

/**
 * DESCRIPTION: TODO
 *
 * @author donghao.ma
 * @date 2019/6/4 10:35
 */
public class Test extends Father{
    private String name = "Test";

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.getName());
    }
}
class Father{
    private String name = "father";
    public String getName(){
        return name;
    }
}
