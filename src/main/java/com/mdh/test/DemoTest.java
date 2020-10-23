package com.mdh.test;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

}
