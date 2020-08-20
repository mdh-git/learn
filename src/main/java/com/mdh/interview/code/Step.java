package com.mdh.interview.code;

/**
 * 有n步台阶,一次只能上1步或2步,共有多少种走法?
 *
 * 递归:
 *      n = 1   f(1) = 1
 *      n = 2   f(2) = 2
 *      n = 3   f(3) = f(2) + f(1)
 *      n = 4   f(4) = f(3) + f(2)
 *      n = x   f(x) = f(x-1) + f(x-2)
 *
 * 循环迭代:
 *
 * @Author : mdh
 * @Date: 2019/3/17
 */
public class Step {
    public static void main(String[] args) {
        int n = 6;
        System.out.println(function(n));
        System.out.println(loop(n));
    }



    /**
     * 递归
     * 实现f(n),求n步台阶,一共有几种走法
     */
    private static int function(int n) {
        if(n < 1){
            new IllegalArgumentException(n + "不能小于1");
        }
        if(n == 1 || n == 2){
            return n;
        }
        return function(n-2) + function(n-1);
    }


    /**
     * 循环迭代
     * 效率高
     * @param n
     */
    private static int loop(int n) {
        if(n < 1){
            new IllegalArgumentException(n + "不能小于1");
        }
        if(n == 1 || n == 2){
            return n;
        }

        //走到第二级台阶的走法
        int one = 2;
        //走到第一级台阶的走法
        int two = 1;
        int sum = 0;

        for(int i = 3; i <= n ;i++){
            //最后走2步 + 最后走一步
            sum = one + two;
            two = one;
            one = sum;
        }
        return sum;
    }

}
