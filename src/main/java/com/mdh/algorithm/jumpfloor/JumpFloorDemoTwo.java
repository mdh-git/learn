package com.mdh.algorithm.jumpfloor;

/**
 * @author madonghao
 *
 * 题目：
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级，…它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法？
 *
 * 通过上面的简单版跳青蛙问题我们很容易就可以得到：
 * f(n) = f(n-1) + f(n-2) + f(n-3) + ... +f(n-(n-1))+f(0)
 *
 * 在往下一级推：
 * f(n-1) = f(n-2) + f(n-3) + ... +f(n-(n-1))+f(0)
 *
 * 所以也就是：
 * f(n) = 2 * f(n-1)
 *
 * 特殊情况（递归停止）：
 * f(0) = 0;
 * f(1) = 1;
 */
public class JumpFloorDemoTwo {
    public static void main(String[] args) {
        System.out.println(JumpFloorII(3));
    }

    public static int JumpFloorII(int target) {
        if(target == 0) {
            return 0;
        }
        if(target == 1) {
            return 1;
        }
        return 2*JumpFloorII(target-1);
    }

}
