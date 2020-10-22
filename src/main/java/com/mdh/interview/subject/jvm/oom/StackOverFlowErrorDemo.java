package com.mdh.interview.subject.jvm.oom;

/**
 * @author MDH
 * 2020/10/20 22:16
 *
 *Exception in thread "main" java.lang.StackOverflowError
 * 递归调用,栈空间不足
 * 是Error
 */
public class StackOverFlowErrorDemo {
    public static void main(String[] args) {
        stackOverFlowError();
    }

    private static void stackOverFlowError() {
        stackOverFlowError();//Exception in thread "main" java.lang.StackOverflowError
    }
}
