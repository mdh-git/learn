package com.mdh.datastructure.stackinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author madonghao
 * @date 2018/10/26
 */
public class CharStack {
    private int size;
    private int top;
    private char[] stackArray;

    /**
     * 构造函数
     * @param size
     */
    public CharStack(int size) {
        stackArray = new char[size];
        //初始化栈的时候，栈内无元素，栈顶下标设为-1
        top = -1;
        this.size = size;
    }

    /**
     * 入栈，同时，栈顶元素的下标加一
     * @param elem
     */
    public void push(char elem) {
        stackArray[++top] = elem;
    }

    /** 出栈，删除栈顶元素，同时，栈顶元素的下标减一 */
    public char pop() {
        return stackArray[top--];
    }

    /** 查看栈顶元素，但不删除 */
    public char peek(){
        return stackArray[top];
    }

    /** 判空 */
    public boolean isEmpty(){
        return (top == -1);
    }

    /** 判满 */
    public boolean isFull(){
        return (top == size - 1);
    }

    public static void main(String[] args) {

        System.out.println("输入需要检测的字符串：");
        String str = getString();
        BrecketChecker checker = new BrecketChecker(str);
        checker.check();
    }

    public static String getString() {
        String str = "";
        try {
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
