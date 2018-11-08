package com.mdh.classtest;

import java.util.ArrayList;
import java.util.List;

/**
 * java中获取class类的方法:
 * (1): Class class = Class.forName("类的路径")
 * (2): 类名.class
 * (3): 实例.getClass()
 * 获取当前类的字节码
 * new ArrayList<String>() 和 new ArrayList<Integer>() 都是属于ArrayList这个类，字节码相同
 * @author madonghao
 * @date 2018/11/2
 */
public class ClassTest {

    private String name;

    public ClassTest(String name){
        this.name = name;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        //第一种:
        Class c1 =  Class.forName("com.mdh.classtest.ClassTest");
        System.out.println("第一种:" + c1);
        //第二种:
        Class c2 = ClassTest.class;
        System.out.println("第二种:" + c2);
        //第三种:
        ClassTest classTest = new ClassTest("名字");
        Class c3 = classTest.getClass();
        System.out.println("第三种:" + c3);

        List<String> stringArrayList = new ArrayList<String>();
        stringArrayList.add("1");
        List<Integer> integerArrayList = new ArrayList<Integer>();
        integerArrayList.add(1);

        Class classStringArrayList = stringArrayList.getClass();
        Class classIntegerArrayList = integerArrayList.getClass();

        /**
         * 下面的例子可以证明，在编译之后程序会采取去泛型化的措施。也就是说Java中的泛型，只在编译阶段有效。
         * 在编译过程中，正确检验泛型结果后，会将泛型的相关信息擦出，并且在对象进入和离开方法的边界处添加类型检查和类型转换的方法。也就是说，泛型信息不会进入到运行时阶段。
         * 对此总结成一句话：泛型类型在逻辑上看以看成是多个不同的类型，实际上都是相同的基本类型。
         */
        if(classStringArrayList.equals(classIntegerArrayList)){
            System.out.println("String:" + classStringArrayList);
            System.out.println("Integer:" + classIntegerArrayList);
            System.out.println("泛型测试,类型相同");
        }

        Generic<Integer> integerGeneric = new Generic<Integer>();
        integerGeneric.setKey(11);
        Generic<Number> numberInteger = new Generic<Number>();
        numberInteger.setKey(11);

        Class classIntegerGeneric = integerGeneric.getClass();
        Class classGenericNumber = numberInteger.getClass();

        if(classIntegerGeneric.equals(classGenericNumber)){
            System.out.println("Integer:" + classIntegerGeneric);
            System.out.println("Number:" + classGenericNumber);
            System.out.println("泛型测试,类型相同.");
        }
    }
}
