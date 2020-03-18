package com.mdh.collection.utils;

import org.junit.Test;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * https://blog.csdn.net/qidasheng2012/article/details/83383389
 *
 * CollectionUtils工具类的操作
 *
 * @author madonghao
 * @create 2020-03-18 16:28
 **/
public class CollectionUtilsTest {

    @Test
    public void test01(){
        List<Integer> lista = Arrays.asList(1, 3, 5);
        List<Integer> listb = Arrays.asList(1, 4, 5);
        Collection subtract = CollectionUtils.subtract(lista, listb);
        subtract.stream().forEach(v -> System.out.println(v));

    }

}
