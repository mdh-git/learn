package com.mdh.algorithm.LeetCode.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * 环形链表
 * https://leetcode-cn.com/problems/linked-list-cycle/
 *
 * @Author: madonghao
 * @Date: 2021/6/9 5:27 下午
 */
public class AnnulusLinkedList {

    public static void main(String[] args) {
        //
        ListNode listNode = nodeInit();
        boolean result = hasCycle1(listNode);
        System.out.println(result);
    }

    /**
     * 判断链表中是否有环
     * 使用快慢指针
     * @param head
     * @return
     */
    public static boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null ){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                return true;
            }
        }
        return false;
    }

    /**
     * 使用哈希表存储
     * 遍历链表，判断哈希表中是否存在当前的元素
     * @param head
     * @return
     */
    public static boolean hasCycle1(ListNode head) {
        Set<ListNode> hashSet = new HashSet<>();
        while (head != null){
            if(!hashSet.add(head)){
                return true;
            }
            head = head.next;
        }
        return false;
    }


    /**
     * 初始化链表
     * @return
     */
    private static ListNode nodeInit() {
        ListNode head = new ListNode(1);
        ListNode listNode1 = new ListNode(2);
        head.next = listNode1;
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = listNode1;
        return head;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int val){
        this.val = val;
    }
}
