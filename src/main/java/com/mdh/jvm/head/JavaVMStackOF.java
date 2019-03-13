package com.mdh.jvm.head;

/**
 * 栈内存溢出，没有dump文件。
 * Exception in thread "main" java.lang.StackOverflowError
 * 设置VM参数  -Xss128k
 * @Author: madonghao
 * @Date: 2019/3/13 15:36
 */
public class JavaVMStackOF {
    int stackLength = 1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }
    public static void main(String[] args){
        JavaVMStackOF oom = new JavaVMStackOF();
        oom.stackLeak();
    }
}
