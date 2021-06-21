package com.mdh.algorithm.sort;

/**
 * 反转链表
 * @Author: madonghao
 * @Date: 2021/6/7 3:49 下午
 */
public class ReverseLinkedList {

   public static void main(String[] args) {
       // 初始化链表
       Node head = nodeInit();
       System.out.print("原始的链表  ");
       printLinkedList(head);

       Node node = reverse(head);
       System.out.print("反转后链表  ");
       printLinkedList(node);
   }

    /**
     * 反转链表
     * @param head
     * @return
     */
    static Node reverse(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 打印链表
     * @param head
     */
    static void printLinkedList(Node head) {
        System.out.print("Linked List: ");
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 初始化链表
     * @return
     */
    private static Node nodeInit() {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        return head;
    }



}

class Node {
    int value;
    Node next;

    public Node(int data) {
        this.value = data;
    }
}
