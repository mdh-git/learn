package com.mdh.algorithm;

/**
 * 阶乘累加求和: 1-n阶乘之和
 * 1的阶乘1
 * 2的阶乘1*2
 * 3的阶乘1*2*3
 * 4的阶乘1*2*3*4
 * ......
 *
 * 思路:
 * 3的阶乘的和 = 2的阶乘的和 + 3的阶乘
 * 4的阶乘的和 = 3的阶乘的和 + 4的阶乘
 *
 * @Author : mdh
 * @Date: 2019/4/22
 */
public class Factorial {
    public static void main(String[] args) {
        System.out.println(factorial(3));
    }

    private static int factorial(int n) {

        //总和
        int sum = 0;
        //阶乘值(每次的阶乘值),初始化值为1(因为是乘法，初始值为1)
        int factorial = 1;
        for(int i = 1; i <= n;i++){
            factorial *= i;
            sum += factorial;
        }
        return sum;
    }
}
