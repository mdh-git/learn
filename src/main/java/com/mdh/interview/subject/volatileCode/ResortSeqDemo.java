package com.mdh.interview.subject.volatileCode;

/**
 * 禁止指令重排
 */
public class ResortSeqDemo {

    int a = 0;
    boolean flag = false;

    public void method1(){
        a = 1;
        // 多线程情况下指令重排造成这句话先执行
        flag = true;
    }

    // 多线程环境中线程交替执行，由于编译器优化重排的存在，
    // 两个线程中使用的变量能否保证一致性是无法确认的，结果无法预知
    public void method2(){
        if(flag){
            a = a + 5;
            System.out.println("**U***retValue:" + a);
        }
    }
}
