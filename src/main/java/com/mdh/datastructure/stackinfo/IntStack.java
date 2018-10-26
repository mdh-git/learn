package com.mdh.datastructure.stackinfo;

import java.util.Arrays;

/**
 * 栈的访问是受限制的，即在特定时刻只有一个数据项可以被读取或删除
 * 栈的主要机制可用数组来实现，也可以用链表来实现。
 * 栈只允许访问一个数据项：即最后插入的数据。移除这个数据项后才能访问倒数第二个插入的数据项。
 * 它是一种“后进先出”的数据结构。
 * 栈最基本的操作是出栈（Pop）、入栈（Push）
 * @author madonghao
 * @date 2018/10/26
 */
public class IntStack {
    /** 栈的大小 */
    private int size;
    /** 栈顶的元素 */
    private int top;
    /** 栈的容器 */
    private int [] stackArray;

    public IntStack(int size) {
        stackArray = new int[size];
        //初始化栈的时候，栈内无元素，栈顶下标设为-1
        top = -1;
        this.size = size;
    }

    /**
     * 入栈，同时，栈顶元素的下标加一
     * @param elem
     */
    public void push(int elem) {
        //插入栈顶
        stackArray[++top] = elem;
    }

    /**
     * 出栈，删除栈顶元素，同时，栈顶元素的下标减一
     * @return
     */
    public int pop() {
        return stackArray[--top];
    }

    /**
     * 查看栈顶元素，但不删除
     * @return
     */
    public int peek() {
        return stackArray[top];
    }

    /**
     * 判空
     * @return
     */
    public boolean isEmpty() {
        return (top == -1);
    }

    /**
     * 判满
     * @return
     */
    public boolean isFull() {
        return (top == size - 1);
    }

    public static void main(String[] args) {
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        stack.push(2);
        stack.push(3);
        stack.push(5);
        stack.push(4);
        stack.push(6);
        stack.push(9);
        System.out.println(Arrays.toString(stack.toArray()));
        System.out.println("------------------------------");
        IntStack stack1 = new IntStack(5);
        System.out.println(stack1.isEmpty());
        System.out.println(stack1.size);
        stack1.push(3);
        stack1.push(13);
        stack1.push(23);
        stack1.push(33);
        System.out.println(stack1.isFull());
        stack1.push(43);
        System.out.println(stack1.isFull());
        // 出栈前需要应该保证栈中有元素
        // 在入栈前应保证栈没有满
        stack1.pop();
        System.out.println(stack1.isFull());
        System.out.println(stack1.peek());

    }
}
