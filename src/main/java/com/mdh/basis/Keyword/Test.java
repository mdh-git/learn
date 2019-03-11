package com.mdh.basis.Keyword;

/**
 * @Author: madonghao
 * @Date: 2019/3/11 17:38
 */
public class Test {
    public Test() {
        System.out.println("test constructor");
    }

    Person person = new Person("Test");
    static{
        System.out.println("test static");
    }


    public static void main(String[] args) {
        new MyClass();
    }
}

class Person{
    static{
        System.out.println("person static");
    }
    public Person(String str) {
        System.out.println("person "+str);
    }
}


class MyClass extends Test {
    Person person = new Person("MyClass");
    static{
        System.out.println("myclass static");
    }

    public MyClass() {
        System.out.println("myclass constructor");
    }
}
