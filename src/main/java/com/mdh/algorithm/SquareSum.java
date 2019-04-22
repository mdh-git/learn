package com.mdh.algorithm;

/**
 * 1! + 4!(2的平方) + 9!(3的平方) + ...n!的值
 * @Author : mdh
 * @Date: 2019/4/22
 */
public class SquareSum {
    public static void main(String[] args) {
        System.out.println(squareSum(3));
        System.out.println(1 + 4*3*2 + 9*8*7*6*5*4*3*2);
    }

    private static int squareSum(int n) {
        //总和
        int sum = 0;
        for(int i = 1; i <= n; i++){
            //得到平方数
            int squre = i * i;
            //一定要在第一层循环里面定义,每次都初始化成1
            int factorial = 1;
            //求第i项的阶乘
            for(int j = 1;j <= squre;j++){
                factorial *= j;
            }
            sum += factorial;
        }
        return sum;
    }
}
