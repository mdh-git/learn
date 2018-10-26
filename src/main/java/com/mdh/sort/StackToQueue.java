package com.mdh.sort;

import java.util.Stack;

/**
 * 利用两个栈实现队列
 * @author madonghao
 * @date 2018/10/25
 */
public class StackToQueue {

    /** 实现两个栈 */
    private Stack<Integer> stackIn = new Stack<Integer>();
    private Stack<Integer> stackOut = new Stack<Integer>();

    /**
     * 入队操作
     * @param element 入队的元素
     */
    public void enQueue(int element) {
        stackIn.push(element);
    }

    /**
     * 出队操作
     * @return 出队的元素
     */
    public Integer deQueue() {
        if(stackOut.isEmpty()) {
            if(stackIn.isEmpty()) {
                return null;
            }
            transfer();
        }
        return stackOut.pop();
    }

    /**
     * 栈stackIn元素转移到栈stackOut中
     */
    private void transfer() {
        while (! stackIn.isEmpty()) {
            stackOut.push(stackIn.pop());
        }
    }

    public static void main(String[] args) {
        StackToQueue stackToQueue = new StackToQueue();
        stackToQueue.enQueue(1);
        stackToQueue.enQueue(2);
        stackToQueue.enQueue(3);
        System.out.println(stackToQueue.deQueue());
        System.out.println(stackToQueue.deQueue());
        stackToQueue.enQueue(5);
        stackToQueue.enQueue(4);
        System.out.println(stackToQueue.deQueue());
        System.out.println(stackToQueue.deQueue());
        System.out.println(stackToQueue.deQueue());
    }
}
