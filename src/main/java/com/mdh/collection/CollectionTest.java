package com.mdh.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @Author: madonghao
 * @Date: 2019/2/14 14:45
 */
public class CollectionTest {
    public static void main(String[] args) {
        Collection c = new ArrayList();
        c.add("hello");
        c.add("world");
        c.add("java");

        Iterator it = c.iterator();
        while (it.hasNext()){
            String str = (String)it.next();
            System.out.println(str);
        }

    }
}
