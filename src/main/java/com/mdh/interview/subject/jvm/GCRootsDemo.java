package com.mdh.interview.subject.jvm;

/**
 * @author MDH
 * 2020/9/23 22:32
 * 在java中,可作为GC Roots的对象有
 * 1.虚拟机栈(栈帧中的本地变量表)中引用的对象
 * 2.方法区的类静态属性引用的对象
 * 3.方法区的常量引用的对象
 * 4.本地方法栈中JNI(Native方法)引用的对象
 */
public class GCRootsDemo {

    private byte[] byteArray = new byte[100 * 1024 * 1024];

    // private  static GCRootsDemo2 t2;
    // private  static final GCRootsDemo3 t3 = new GCRootsDemo3(8);

    // 方法是在栈中
    public static void m1(){
        // 虚拟机栈中引用的对象
        GCRootsDemo t1 = new GCRootsDemo();
        System.gc();
        System.out.println("第一次GC完成");
    }

    public static void main(String[] args) {
        m1();
    }
}
