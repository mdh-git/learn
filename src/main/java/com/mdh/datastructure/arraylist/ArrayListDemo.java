package com.mdh.datastructure.arraylist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author madonghao
 * @date 2018/11/8
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        //无参构造函数：初始化一个长度为0的空数组
        List<String> list = new ArrayList<>();
        list.add("一");
        list.add("二");
        System.out.println("无参构造函数: " + list);

        //ArrayList(int) 构造函数：初始化一个指定长度的数组
        List<String> listInt = new ArrayList<>(3);
        listInt.add("一");
        listInt.add("二");
        listInt.add("三");
        System.out.println("ArrayList(int) 构造函数: " + listInt);

        Collection<? extends String> collection = listInt;
        List<String> listColl = new ArrayList<>(collection);
        listColl.add("四");
        System.out.println("ArrayList(Collection<? extends E>)构造函数: " + listColl);

        System.out.println();

        Iterator<String> listIter = list.iterator();
        while (listIter.hasNext()) {
            //array.add(4);  add() 和remove()会导致modCount发生变化,从而导致迭代过程中抛出异常
            String value = listIter.next();
            //使用迭代器提供的remove()方法避免抛异常，原因：迭代器的remove方法在删除元素之后对将ArrayList的modCount覆盖了迭代器类的expectedModCount
            //listIter.remove();
            System.out.println("迭代器iterator遍历: " + value);
        }
        for (String iter:list) {
            //item.add()和item.remove()都将报错
            System.out.println("forEach遍历: " + iter);
        }



    }
}
