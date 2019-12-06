package com.mdh.algorithm.LeetCode.medium;

import lombok.Data;

/**
 * LeetCode算法的第二道题目(使用)
 *
 * @author madonghao
 * @create 2019-11-06 16:12
 **/
@Data
public class ListNode {
    private int val;
    private ListNode next;
    public ListNode(int x) { val = x; }
    public void getList(ListNode node) {
    while(node!=null) {
        System.out.println(node.val);
        node=node.next;
        }
    }
}
