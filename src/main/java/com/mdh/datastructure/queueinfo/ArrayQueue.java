package com.mdh.datastructure.queueinfo;

/**
 * 顺序队列
 * 循环队列:当队列没有元素时，head 等于tail ,而当队列满了时，head 也等于 tail，
 * 为了区分这两种状态，一般在循环队列中规定队列的长度只能为数组的长度减1，即有一个位置不放元素。
 * 当head 等于tail 时，说明队列为空队，而当head 等于(tail + 1) % length 时(length为数组长度)，说明队满。
 * @author madonghao
 * @date 2018/11/19
 */
public class ArrayQueue {

    public static void main(String[] args) {
        int num = 7%10;
        System.out.println(num);
    }
}
