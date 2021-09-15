package com.mdh.algorithm.LeetCode.simple;

/**
 *
 * @Author: madonghao
 * @Date: 2021/8/23 11:25 上午
 *
 * https://leetcode-cn.com/problems/get-maximum-in-generated-array/
 * 1646. 获取生成数组中的最大值
 */
public class MaximumGenerated {

    /**
     * 给你一个整数 n 。按下述规则生成一个长度为 n + 1 的数组 nums ：
     *
     * nums[0] = 0
     * nums[1] = 1
     * 当 2 <= 2 * i <= n 时，nums[2 * i] = nums[i]
     * 当 2 <= 2 * i + 1 <= n时，nums[2 * i + 1] = nums[i] + nums[i + 1]
     * 返回生成数组 nums 中的 最大 值。
     *
     * @param n
     * @return
     */
    public static int getMaximumGenerated(int n) {

        if(n == 0 || n == 1){
            return n;
        }

        int max = 0;


        return 0;
    }

    public static void main(String[] args) {

    }
}
