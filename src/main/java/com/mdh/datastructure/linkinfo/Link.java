package com.mdh.datastructure.linkinfo;

/**
 * 单链表
 * 链结点的封装类
 * @author madonghao
 * @date 2018/10/29
 */
public class Link {

    public int age;
    public String name;
    /** 指向该链结点的下一个链结点 */
    public Link next;

    /**
     * 构造方法
     * @param age
     * @param name
     */
    public Link(int age, String name){
        this.age = age;
        this.name = name;
    }

    /**
     * 打印该链结点的信息
     */
    public void displayLink(){
        System.out.println("name:" + name + ",age:" + age);
    }
}
