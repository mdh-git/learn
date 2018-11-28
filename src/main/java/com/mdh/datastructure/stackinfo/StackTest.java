package com.mdh.datastructure.stackinfo;

import java.util.Enumeration;
import java.util.Stack;

/**
 *
 * @author madonghao
 * @date 2018/11/19
 */
public class StackTest {

    public static void main(String[] args) {
        Stack<String> stackInfo = new Stack<>();
        stackInfo.push("A");
        stackInfo.push("B");
        System.out.println(stackInfo.pop());
        stackInfo.push("C");
        System.out.println(stackInfo.search("A"));
        stackInfo.push("D");
        System.out.println(stackInfo.search(""));
        System.out.println(stackInfo.search(null));
        System.out.println(stackInfo.search("d"));
        System.out.println(stackInfo.search("D"));
        System.out.println(stackInfo.search("E"));
        System.out.println(stackInfo.pop());

        // 得到 stack 中的枚举对象
        Enumeration<String> items = stackInfo.elements();
        //显示枚举（stack ） 中的所有元素
        while(items.hasMoreElements()){
            System.out.print(items.nextElement()+" ");
        }
    }
}
