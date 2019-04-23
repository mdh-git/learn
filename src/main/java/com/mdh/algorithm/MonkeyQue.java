package com.mdh.algorithm;

/**
 * 猴子吃桃子问题:
 * 猴子摘下了n个桃子，当天吃掉一半多一个，
 * 第二天也是吃掉剩下桃子的一半多一个，到了第十天，桃子只剩下了1个。
 * 问:猴子第一天摘了多少个桃子
 *
 * 思路:
 * 假设当天有n个桃子，它是前一天桃子的一半少1个，f(n - 1) = f(n)/2 - 1,
 * 我们就可以推出当天桃子的个数：根据递推公式：f(n) = 2 * f(n - 1) + 2
 *
 * @Author donghao.ma
 * @create 2019/4/23 21:28
 */
public class MonkeyQue {
    public static void main(String[] args) {
        System.out.println(monkeyQuWithRecursion(10));
        System.out.println(monkeyQuWithFor(10));
    }


    /**
     * @param x 天数
     * @return
     */
    private static int monkeyQuWithRecursion(int x) {
        if( x<=0 ){
            return 0;
        }else if(x == 1){
            return 1;
        }else {
            return 2 * monkeyQuWithRecursion(x - 1) + 2;
        }
    }

    /**
     * 后一天 = 前一天 / 2  -1
     * 前一天 = 2 * (后一天 + 1)
     * @param x 天数
     * @return
     */
    private static int monkeyQuWithFor(int x) {
        //第10天为1个
        int t = 1;
        for (int i = 1; i < x; i++) {
            t = 2 * (t + 1);
        }
        return t;
    }
}
