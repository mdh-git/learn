package com.mdh.jvm;

/**
 * 栈溢出测试
 * 运行多次,每次栈的深度都是不一样的
 * @author madonghao
 * @create 2020-05-14 14:07
 **/
public class StackErrorMock {
    private static int index = 1;

    public void call(){
        index++;
        call();
    }

    public static void main(String[] args) {
        StackErrorMock mock = new StackErrorMock();
        try {
            mock.call();
        } catch (Throwable e){
            System.out.println("Stack deep:" + index);
            e.printStackTrace();
        }
    }
}
