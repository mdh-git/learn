package com.mdh.algorithm;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author madonghao
 * @create 2020-09-16 16:19
 *
 * java8实现排列组合
 **/
public class PermutationsDemo {

    /**
     * 给定两个数字列表,如何返回所有的数对？
     * 例如,给定列表[1, 2, 3]和列表[3,4]
     * 结果,[(1,3),(1,4),(2,3),(2,4),(3,3),(3,4)]
     *
     * 使用两个map来迭代这两个列表,并生成成对数,但这样会返回一个Stream<Stream<Integer[]>>
     * 然后扁平化,以得到一个Stream<Integer[]>
     */
    @Test
    public void one(){
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(3, 4);
        list1.stream().map(
                i -> list2.stream().map(
                            j -> new int[]{i, j}
                    )
        ).collect(Collectors.toList());
    }

    @Test
    public void two(){
        List<String> list1 = Arrays.asList("1", "2", "3");
        List<String> collect = list1.stream().flatMap(i -> list1.stream().map(i::concat)).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 有重复字符串的排列组合
     * 排列组合（字符重复排列）
     * 内存占用：需注意结果集大小对内存的占用（list:10位，length:8，结果集:[10 ^ 8 = 100000000]，内存占用:约7G）
     * @param list 待排列组合字符集合
     * @param length 排列组合生成的字符串长度
     * @return 指定长度的排列组合后的字符串集合
     */
    public static List<String> permutation(List<String> list, int length) {
        Stream<String> stream = list.stream();
        for (int n = 1; n < length; n++) {
            stream = stream.flatMap(str -> list.stream().map(str::concat));
        }
        return stream.collect(Collectors.toList());
    }

    /**
     * 无重复字符串的排列组合
     * 排列组合(字符不重复排列)
     * 内存占用：需注意结果集大小对内存的占用（list:10位，length:8，结果集:[10! / (10-8)! = 1814400]）
     * @param list 待排列组合字符集合(忽略重复字符)
     * @param length 排列组合生成长度
     * @return 指定长度的排列组合后的字符串集合
     */
    public static List<String> permutationNoRepeat(List<String> list, int length) {
        Stream<String> stream = list.stream().distinct();
        for (int n = 1; n < length; n++) {
            stream = stream.flatMap(str -> list.stream()
                    .filter(temp -> !str.contains(temp))
                    .map(str::concat));
        }
        return stream.collect(Collectors.toList());
    }
}
