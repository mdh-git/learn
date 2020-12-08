package com.mdh.algorithm.jumpfloor;


/**
 * @author madonghao
 *
 * 题目：
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 *
 * 解法：
 * 把n级台阶时的跳法记为f(n)，当n>2时，
 * 第一次跳的时候有两种不同的选择：
 * （1）第一次只跳1级，此时跳法数目等于后面剩下的n-1级台阶的跳法数目，即为f(n-1);
 * （2）第一次跳2级，此时跳法数目等于后面剩下的n-2级台阶的跳法数目，即为f(n-2);因此n级台阶时的跳法为f(n)=f(n-1)+f(n-2)
 * eg:
 * 当n=4的时候：
 * f(4) = f(3) + f(2);
 * f(3) = 2 ----->3级台阶口算可得是2（1、2或者2、1）
 * f(2) = 1 ----->2级台阶口算可得是1（只有1、1）
 * 而这也就是斐波拉契数列的变形应用，只不过f(0)=0，f(1)=1。
 */
public class JumpFloorDemo {
    public static void main(String[] args) {
        System.out.println(JumpFloor(3));
    }

    public static int JumpFloor(int target) {
        int pre = 1, next = 1, sum = 0, i = 0;
        if (target <= 1) {
            return pre;
        }
        for (i = 1; i < target; i++) {
            sum = pre + next;
            pre = next;
            next = sum;
        }
        return sum;
    }
}
