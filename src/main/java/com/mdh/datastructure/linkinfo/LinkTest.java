package com.mdh.datastructure.linkinfo;

import java.util.LinkedList;

/**
 *
 * @author madonghao
 * @date 2018/11/1
 */
public class LinkTest {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList();
        linkedList.add("A");
        linkedList.add("b");
        linkedList.add("C");
        linkedList.add("D");
        System.out.println(linkedList.element());
        System.out.println(linkedList.getFirst());
        System.out.println(linkedList.getLast());
        System.out.println(linkedList.poll());
        System.out.println(linkedList.pop());
    }
}
