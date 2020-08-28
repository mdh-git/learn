package com.mdh.optional;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author madonghao
 * @create 2020-08-28 15:49
 **/

public class StreamDemo {

    @Test
    public void test01(){
        List<String> words = Arrays.asList("Hello","World");

        List<String> collect = words.stream().map(t -> t.split("")).flatMap(t -> Arrays.stream(t)).distinct().collect(Collectors.toList());

        System.out.println(collect);
    }
}
