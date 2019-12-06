package com.mdh.algorithm.LeetCode.medium;

import org.junit.Test;

/**
 * LeetCode算法的第二道题目：
 *
 * 2. 两数相加
 *
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * @author madonghao
 * @create 2019-11-06 16:09
 **/
public class AddTwoNumbers {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // int maxLen = l1.
        int i = 0;
        int j = 0;
        int num1 = 0;
        int num2 = 0;
        double base = 10d;
        while (l1 != null){
            num1 += l1.getVal() * Math.pow( base,   i);
            i++;
            l1 = l1.getNext();
        }
        System.out.println(num1);
        while (l2 != null){
            num2 += l2.getVal() * Math.pow( base,   j);
            j++;
            l2 = l2.getNext();
        }
        System.out.println(num2);
        int sum = num1 + num2;
        String strSum = String.valueOf(sum);
        System.out.println("sum:" + strSum);
        ListNode result = new ListNode(0);
        for(int x = 0; x < strSum.length();x++){
            Integer integer = Integer.valueOf(String.valueOf(strSum.charAt(x)));
            System.out.println("1:" + integer);
            result.setVal(integer);
            ListNode first = new ListNode(0);
            first.setNext(result);
            result = first;
            if(x == strSum.length() -1){
                result = result.getNext();
            }
        }
        return result;
    }

    @Test
    public void solutionOne() {
        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(1);
        l2.setNext(new ListNode(9));
        l2.getNext().setNext(new ListNode(9));
        System.out.println();
        l1.getList(l1);
        System.out.println("---------------");
        l2.getList(l2);
        ListNode listNode = addTwoNumbers(l1, l2);
        System.out.println("---------------");
        listNode.getList(listNode);
    }
}
