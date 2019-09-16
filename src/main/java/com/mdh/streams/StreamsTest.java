package com.mdh.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * stream的一些操作
 *
 * @author donghao.ma
 * @date 2019/9/11 10:36
 *
 * https://www.jianshu.com/p/0c07597d8311
 */
public class StreamsTest {
    public static void main(String[] args) {

        // stream是一个可以对个序列中的元素执行各种计算操作的一个元素序列。
        List<String> list = Arrays.asList("a1", "b1", "c1", "c2", "c3");
        list.stream()
                .filter( v -> v.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);


        // findFirst
        Arrays.asList("a1", "b1", "c1", "c2")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);

        // 直接使用Stream.of()方法就能从一组对象创建一个stream对象
        Stream.of("a1", "b1", "c1", "c2")
                .findFirst()
                .ifPresent(System.out::println);

        // JAVA 8中的IntStream,LongStream,DoubleStream
        IntStream.range(1, 5)
                .forEach(System.out::println);

        // 基本类型流（primitive streams）使用方式与常规对象流类型（regular object streams）大部分相同，
        // 但是基本类型流（primitive streams）能使用一些特殊的lambda表达式
        Arrays.stream(new int []{1, 2, 3})
                .map( n -> 2*n +1)
                .average()
                .ifPresent(System.out::println);

        IntStream.range(1, 4)
                .mapToObj( i -> "a" + i)
                .forEach(System.out::println);

        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj( i -> "b" + i)
                .forEach(System.out::println);

    }
}
