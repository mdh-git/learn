package com.mdh.test;

import com.google.common.collect.Lists;
import com.mdh.datastructure.linkinfo.LinkList;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * @author madonghao
 * @create 2020-07-03 15:32
 **/
public class DemoTest {

    @Test
    public void test01(){
        int [] intArray = { 1 ,  2 ,  3 ,  4 ,  5 };
        //List<Integer> list = Arrays.asList(intArray); 编译通不过
        List<int[]> ints = Arrays.asList(intArray);
        System.out.println(ints);

        Integer [] array = {1 ,  2 ,  3 ,  4 ,  5 };
        List<Integer> list = Arrays.asList(array);
        System.out.println(list);

        ArrayList arrayList = new ArrayList() {{
                add("2");
                add("3");
            }};
        System.out.println(arrayList);
    }

    @Test
    public void test02(){
        String str = "TodAy is a Nice day";
        String[] s = str.split(" ");
        Stack stack = new Stack();
        for (int i = 0; i < str.length(); i++) {
            char char1 = str.charAt(i);
            if(char1 != ' '){
                stack.push(char1);
            } else {
                while (!stack.empty()){
                    System.out.print(stack.pop().toString());
                }
                System.out.print(" ");
            }
        }

        while (!stack.empty()){
            System.out.print(stack.pop().toString());
        }
    }

    /**
     * "TodAy is a Nice day"
     * "YadOt si a Ecin yad "
     */
    @Test
    public void test03(){
        String str = "TodAy is a Nice day";
        String[] s = str.split(" ");
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length(); j++) {
                char c = s[i].charAt(j);
                // 当前的是大写
                if('A' <= c && c<= 'Z') {
                    char c1 = s[i].charAt(s[i].length() - j -1);
                    if('A' <= c1 && c1<= 'Z'){
                        System.out.print(c1);
                    } else {
                        c1-=32;
                        System.out.print(c1);
                    }
                } else {
                    // 小写
                    char c1 = s[i].charAt(s[i].length() - j -1);
                    if('A' <= c1 && c1<= 'Z'){
                        c1 += 32;
                        System.out.print(c1);
                    } else {
                        System.out.print(c1);
                    }
                }
            }

            System.out.print(" ");
        }
    }

    @Test
    public void test04(){
        String x = "TodAy is a Nice day";
        String[] s = StringUtils.split(x, " ");
        for (String v : s) {
            char[] chars = v.toCharArray();
            for (int i=0, j = chars.length - 1; j >= 0; j--) {
                char aChar = chars[i++];
                System.out.print(Character.isUpperCase(aChar) ? Character.toUpperCase(chars[j]) : Character.toLowerCase(chars[j]));
            }
            System.out.print(' ');
        }
    }

    @Test
    public void test05(){
        Node head1 = nodeInit();
        System.out.println("========原始链表========");
        printLinkedList(head1);
        Node head2 = nodeInit();
        Node res2 = reverseList2(head2);
        printLinkedList(res2);
    }

    @Test
    public void test06(){
        LinkedList list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        for (int i = 0; i < list.size(); i++) {
             list.add(i, list.removeLast());
        }
        System.out.println(list);
    }

    /**
     * 初始化链表用例
     * @return
     */
    static Node nodeInit() {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        return head;
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
     * https://github.com/Hollake/Algorithm/blob/master/ReverseList.java
     * https://www.cnblogs.com/luego/p/11421590.html
     * 非递归反转链表
     * @param head
     * @return
     */
    static Node reverseList2(Node head) {
        Node pre = null;
        Node next = null;
        while ( head != null ) {
            //next保存head下一个节点
            next = head.next;
            //将当前节点next指向前一个节点，实现反转
            head.next = pre;
            //将当前节点的前节点后移
            pre = head;
            //将当前节点后移
            head = next;
        }
        return pre;
    }

}

class Node {
    public int value;
    public Node next;

    public Node(int data) {
        this.value = data;
    }
}
