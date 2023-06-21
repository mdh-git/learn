package com.mdh.algorithm.interview.xiaohongshu;

import java.util.Arrays;

/**
 * 小红书 化学溶液问题
 * 实验室需要配置一种溶液，现在研究员面前有n种该物质的溶液
 * 每一种有无限多瓶，第i种的溶液体积为v[i]，里面含有w[i]单位的改物质
 * 研究员每次可以选择一瓶溶液，
 * 将其倒入另外一瓶（假设瓶子的容量无限），即可以看作将两个瓶子内的溶液合并
 * 此时合并的溶液体积和物质含量都等于之前两个瓶子内的之和。
 * 特别地，如果瓶子A与B的溶液体积相同，那么A与B合并之后
 * 该物质的含量会产生化学反应，使得该物质含量增加X单位
 * 研究员的任务是配制溶液体积恰好等于C的，且尽量浓的溶液（即物质含量尽量多）
 * 研究员想要知道物质含量最多是多少
 * 对于所有数据， 1<=n, v[i], w[i], x,c <= 1000
 *
 *
 * 举例：
 * 规格：
 * 0号规格： 体积5， 含量2
 * 1号规格： 体积3， 含量4
 * 2号规格： 体积4， 含量1
 *
 * （体积5，含量2） + （体积3，含量4） = （体积 5+3=8，含量 2+4=6）
 * 特别的：
 * （体积5，含量4） + （体积5，含量3） = （体积 5+5=10，含量 4+3+x）
 * （体积5，含量2） + （体积5，含量2） = （体积 5+5=10，含量 2+2+x）
 * x 一个固定的整数，输入参数
 * 体积一定达到c的时候，得到的最多含量是多少？
 *
 * 动态规划解决
 */
public class Algorithm_3 {

    public static void main(String[] args) {

        int[] v = {5, 3, 4};
        int[] w = {2, 4 ,1};
        int x = 4;
        int c = 16;
        // （体积3，含量4） + （体积3，含量4） = （体积 3+3=6，含量 4+4+x(4) = 12）
        // （体积6，含量12） + （体积6，含量12） = （体积 6+6=12，含量 12+12+x(4) = 28）
        // （体积12，含量28） + （体积4，含量1） = （体积 12+4=16，含量 28+1 = 29）
        System.out.println(maxValue(v, w, x, c));
    }

    private static int maxValue(int[] v, int[] w, int x, int c) {
        int length = v.length;
        // 求出每种体积的最大值
        int[] dp = new int[c + 1];
        // 初始化默认所有的体积都没有最优解
        Arrays.fill(dp, -1);
        // 把最大容量为自身方案填充
        for (int i = 0; i < v.length; i++) {
            int key = v[i];
            // 溶液体积存在超过最大体积的情况
            if(key <= c){
                dp[key] = Math.max(dp[key], w[i]);
            }
        }

        printlnArr(dp);

        // 求出dp[i]中所有的可能中的最大值
        // dp[10] = dp[1] + dp[9]
        // dp[10] = dp[2] + dp[8]
        // dp[10] = dp[3] + dp[7]
        // dp[10] = dp[4] + dp[6]
        // dp[10] = dp[5] + dp[5] + x

        for (int i = 1; i <= c ; i++) {
            for (int j = 1; j <= i/2; j++){
                // dp[i] = dp[j] + dp[i-j]
                if(dp[j] != -1 && dp[i-j] != -1){
                    dp[i] = Math.max(dp[i], dp[j] + dp[i - j] + ((j == i - j) ? x : 0));
                }
            }
        }
        return dp[c];
    }

    private static void printlnArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.println("");
    }
}
