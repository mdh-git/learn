package com.mdh.algorithm;

import static java.lang.Math.min;

/**
 * 贪心算法
 *
 * https://www.cxyxiaowu.com/852.html
 *
 * 从问题的某一初始解出发
 *     while (能朝给定总目标前进一步)
 *         do
 *             选择当前最优解作为可行解的一个解元素；
 *     由所有解元素组合成问题的一个可行解。
 *
 *
 *
 * 小明手中有 1，5，10，50，100 五种面额的纸币，每种纸币对应张数分别为 5，2，2，3，5 张。若小明需要支付 456 元，则需要多少张纸币？
 * 制定的贪心策略为：在允许的条件下选择面额最大的纸币。
 * @author madonghao
 * @create 2020-01-15 13:57
 **/
public class GreedyTest {
    public static void main(String[] args) {
        // 五种纸币的面额
        int[] quota = { 1, 5, 10, 50, 100};
        // 五种纸币对应的数量
        int[] count = { 5, 2, 2, 3, 5};

        int money = 456;
        getResult(money, quota, count);
        System.out.println("---------------------");
        solve(money, quota, count);
    }

    private static int solve(int money, int[] quota, int[] count) {
        int n = quota.length;
        while (money > 0) {
            int num = 0;
            for(int i = n-1;i>=0;i--) {
                int c = min(money/quota[i],count[i]);//每一个所需要的张数
                money = money-c*quota[i];
                num += c;//总张数
                System.out.println("金额：" + quota[i] + ",需要" + c + "张");
            }
            if(money>0) num=-1;
            System.out.println("一共需要：" + num + "张");
            return num;
        }
        return -1;
    }

    private static void getResult(int money, int[] quota, int[] count) {
        while (money > 0){
            for (int i = quota.length; i > 0; i--) {
                for(int j = count[i -1]; j > 0; j--){
                    if(money >= quota[i -1]){
                        money = money - quota[i -1];
                        System.out.println("面额："+ quota[i -1]);
                    }
                }
            }
            if(money > 0){
                System.out.println("没有解决方法");
                break;
            }
        }
    }
}
