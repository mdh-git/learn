package com.mdh.algorithm.LeetCode.simple;

import java.util.LinkedList;

/**
 * @Author: madonghao @Date: 2021/3/22 2:46 下午
 *
 * https://leetcode-cn.com/problems/dui-lie-de-zui-da-zhi-lcof/
 *
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)。
 * 若队列为空，pop_front 和 max_value 需要返回 -1
 *
 * 示例 1：
 * 输入: ["MaxQueue","push_back","push_back","max_value","pop_front","max_value"]
 * [[],[1],[2],[],[],[]]
 * 输出: [null,null,null,2,1,2]
 *
 * 示例 2：
 * 输入: ["MaxQueue","pop_front","max_value"] [[],[],[]]
 * 输出: [null,-1,-1]
 */
public class MaxQueue {

    /**
     * 用于元素入队出队
     */
    private LinkedList<Integer> originQueue;

    /**
     * 单调队列，队首元素是originQueue中元素的最大值
     */
    private LinkedList<Integer> maxQueue;

    public MaxQueue() {
        originQueue = new LinkedList<>();
        maxQueue = new LinkedList<>();
    }

    public int max_value() {
        if(maxQueue.isEmpty()){
            return -1;
        }
        return maxQueue.getFirst();
    }

    public void push_back(int value) {
        originQueue.addLast(value);

        // 当有元素存入队列originQueue时，为了保证maxQueue的单调性
        // 如果当前入队元素大于等于maxQueue队尾的元素的最大值
        // 那么就需要将maxQueue队尾的元素出队
        // 直到队列为空或者新的队尾大于入队元素
        while(!maxQueue.isEmpty() && value >= maxQueue.peekLast()){
            maxQueue.removeLast();
        }
        maxQueue.addLast(value);
    }

    public int pop_front() {
        if(originQueue.isEmpty()){
            return -1;
        }

        // 在移除originQueue队首的元素时，如果移除的元素等于maxQueue队首元素值
        // 则需要将maxQueue队首元素出队，因为它已经不再队列originQueue中了
        Integer first = originQueue.removeFirst();
        if(first.equals(maxQueue.peekFirst())){
            maxQueue.removeFirst();
        }
        return  first;
    }

  public static void main(String[] args) {
      MaxQueue maxQueue = new MaxQueue();
      maxQueue.push_back(5);
      maxQueue.push_back(1);
      maxQueue.push_back(2);
      maxQueue.push_back(3);

      int pop = maxQueue.pop_front();
      System.out.println(pop);

      int max = maxQueue.max_value();
      System.out.println(max);
  }
}
