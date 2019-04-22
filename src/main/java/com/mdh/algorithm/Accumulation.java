package com.mdh.algorithm;

/**
 * 累加求和
 * 1
 * 1+2
 * 1+2+3
 * 1+2+3+4
 * .
 * .
 * .
 * 1+2+3+4+5+...+n
 * @Author : mdh
 * @Date: 2019/4/22
 */
public class Accumulation {
    public static void main(String[] args) {
        System.out.println(accumulation(4));
    }

    public static int accumulation(int n){
        //求和
        int sum = 0;
        //初始值，每一次要相加的值
        int factorial = 0;
        for(int i = 1; i <= n;i++){
            factorial += i;
            sum += factorial;
        }
        return sum;
    }
}
