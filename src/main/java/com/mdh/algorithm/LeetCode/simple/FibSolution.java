package com.mdh.algorithm.LeetCode.simple;

/**
 * https://leetcode-cn.com/problems/fibonacci-number/
 *
 * <509. 斐波那契数 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。
 * 该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。
 *
 * F(0) = 0，
 * F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 *
 *
 * @Author: madonghao @Date: 2021/9/10 2:34
 * 下午
 */
public class FibSolution {


    public static void main(String[] args) {
        int fib = fib(3);
        System.out.println(fib);

        System.out.println(fib(4));

        System.out.println(fib(10));
        System.out.println(fib1(10));
    }

    public static int fib(int n) {

        if(n == 0 || n == 1){
            return n;
        }

        return fib(n -1 ) + fib(n - 2);
    }


    public static int fib1(int n) {
        if(n < 2) {
            return n;
        }

        int one = 0, two = 0, three = 1;

        for (int i = 2; i <= n; i++) {
            one = two;
            two = three;
            three = one + two;
        }
        return three;
    }
}
