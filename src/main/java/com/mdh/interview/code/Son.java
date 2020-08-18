package com.mdh.interview.code;

/**
 * 子类的实例方法:
 * (1)、super() [最先执行]
 * (2)、i = test() 成员变量 (按照上下顺序)
 * (3)、子类的非静态代码块 (按照上下顺序)
 * (4)、子类的无参构造器 [最后执行]
 *
 * @Author : mdh
 * @Date: 2019/3/17
 *
 * 子类的初始化<clinit>
 *  (1)j = method();
 *  (2)父类的静态代码块
 *
 *
 * 方法的重写
 *  (1) 不能被重写的
 *          final方法
 *          静态方法
 *          private等子类中不可见方法
 *  (2) 对象的多态
 *          子类如果重写了父类的方法,通过子类对象调用的一定是子类重写过的代码
 *          非静态方法默认的调用对象是this
 *          this对象在构造器或者说<init>方法中就是正在创建的对象
 */
public class Son extends Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.println("Son:(6)");
    }

    Son(){
        System.out.println("Son:(7)");
    }

    {
        System.out.println("Son:(8)");
    }

    @Override
    public int test() {
        System.out.println("Son:(9)");
        return 1;
    }

    public static int method() {
        System.out.println("Son:(10)");
        return 1;
    }

    public static void main(String[] args) {
        Son s1 = new Son();
        System.out.println();
        Son s2 = new Son();
    }
}
