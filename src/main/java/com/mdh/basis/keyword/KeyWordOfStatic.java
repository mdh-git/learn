package com.mdh.basis.keyword;

/**
 * static关键字
 * 方便在没有创建对象的情况下来进行调用（方法/变量）,被static关键字修饰的方法或者变量不需要依赖于对象来进行访问，只要类被加载了，就可以通过类名去进行访问。
 *
 * *********************
 *             构造器不是静态的(https://blog.csdn.net/qq_17864929/article/details/48006835)
 * 首先从Java语言规范对“方法”的定义来说，构造器根本不是“方法”；其次，实例构造器有一个隐式参数，“this”，
 * 在实例构造器中可以访问“this”，可以通过“this”访问到正在初始化的对* 象实例的所有实例成员。
 * 实例构造器无法被隐藏或覆写，不参与多态，因而可以做静态绑定。
 * 从这个意义上可以认为实例构造器是“静态”的，但这种用法与Java语言定义的“静态方法”是两码事。
 *
 * 1、Java的实例构造器只负责初始化，不负责创建对象；Java虚拟机的字节码指令的设计也反映了这一点，有一个new指令专门用于创建对象实例，而调用实例构造器则使用invokespecial指令。
 * 2、“this”是作为实例构造器的第一个实际参数传入的。
 * *********************
 *
 *
 * 1）static方法:
 *      在静态方法中不能访问类的非静态成员变量和非静态成员方法，因为非静态成员方法/变量都是必须依赖具体的对象才能够被调用,
 *      但是在非静态成员方法中是可以访问静态成员方法/变量的。
 *
 * 2）static变量:
 *      static变量也称作静态变量，静态变量和非静态变量的区别是：静态变量被所有的对象所共享，在内存中只有一个副本，它当且仅当在类初次加载时会被初始化。
 *             而非静态变量是对象所拥有的，在创建对象的时候被初始化，存在多个副本，各个对象拥有的副本互不影响。
 *　　  static成员变量的初始化顺序按照定义的顺序进行初始化。
 *
 * 3）static代码块:只会在类加载的时候执行一次
 *      用来形成静态代码块以优化程序性能。
 *      static块可以置于类中的任何地方，类中可以有多个static块。在类初次被加载的时候，会按照static块的顺序来执行每个static块，并且只会执行一次。
 *
 *****************************************************************************************************************************
 *  static关键字的误区
 *
 * 1.static关键字不会改变类中成员的访问权限
 *          Java中的static关键字不会影响到变量或者方法的作用域。
 *          在Java中能够影响到访问权限的只有private、public、protected（包括包访问权限）这几个关键字。
 *
 * 2.可以能通过this访问静态成员变量
 *          在使用工具编写代码的时候this.value,编译器会报错，不应该通过类实例访问静态成员
 *          静态成员变量虽然独立于对象，但是不代表不可以通过对象去访问，所有的静态方法和静态变量都可以通过对象访问（只要访问权限足够）。
 *
 * 3.static不能作用于局部变量
 *          static是不允许用来修饰局部变量。这是Java语法的规定。
 *
 * ******************************************************************
 *  static加载顺序:
 *          根据main方法，先加载main方法当前类的静态方法，在加载父类的静态方法
 *          成员变量先加载父类的属性，在加载父类的构造方法，然后在加载子类的
 *
 * *****************************************************************
 * @Author: madonghao
 * @Date: 2019/3/11 16:18
 */
public class KeyWordOfStatic {
    private static String str1 = "staticProperty";
    private String str2 = "property";

    public static int value = 33;

    public KeyWordOfStatic(){

    }

    public void print1(){
        System.out.println(str1);
        System.out.println(str2);
    }

    public static void print2(){
        System.out.println(str1);
        //静态方法里面引用非静态的变量
        //System.out.println(str2);
        //静态方法里面引用非静态的方法
        //print1();
    }

    private void printValue(){
        //int value = 3;
        //不应该通过类实例访问静态成员 this.value
        System.out.println(value);
    }

    public static void main(String[] args) {
        System.out.println(Person1.name);
        //System.out.println(Person.age);

        new KeyWordOfStatic().printValue();
    }
}

class Person1{
    public static String name = "mdh";
    private static int age = 10;
}