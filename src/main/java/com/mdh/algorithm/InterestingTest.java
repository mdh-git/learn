package com.mdh.algorithm;

import org.junit.Test;

/**
 * 一些比较有意思的算法
 * https://mp.weixin.qq.com/s/S9gUBPTuepZao8qasVljEA
 *
 * @author madonghao
 * @create 2020-02-26 17:49
 **/
public class InterestingTest {

    @Test
    public void test(){
        int num = 15;
        System.out.println(num + "是否为2的幂次方 ：" + isPow(num));

        int n = 5;
        int m = 3;
        System.out.println("约瑟夫环结果：" + fun(n, m));

        int[] arr = {1, 2, 3, 2, 1, 4, 5 ,5 ,4};
        System.out.println("出现一次的数：" + find(arr));
        System.out.println("出现一次的数：" + findOne(arr, 1 , 1));
    }



    /**
     * 判断一个整数 n 是否为 2 的幂次方
     * @param num
     * @return
     */
    private boolean isPow(int num) {
        return (num & (num -1)) == 0;
    }


    /**
     * 经典的约瑟夫环
     * @param n 总共的个数
     * @param m 报的数字
     * @return
     */
    private int fun(int n, int m) {
        return n == 1 ? n : (fun(n - 1, m) + m - 1) % n + 1;
    }

    /**
     * 给你一个整型数组，数组中有一个数只出现过一次，其他数都出现了两次，求这个只出现了一次的数。
     * @param arr
     * @return
     */
    private int find(int[] arr){
        int tmp = arr[0];
        for(int i = 1;i < arr.length; i++){
            tmp = tmp ^ arr[i];
        }
        return tmp;
    }

    /**
     * 例如使用这个函数的时候，我们最开始传给 i 的值是 1，传给 result 的是 arr[0]
     * 例如 findOne(arr, 1, arr[0])
     * @param arr
     * @param i
     * @param result
     * @return
     */
    private int findOne(int[] arr,int i, int result){
        return arr.length <= i ? result : findOne(arr, i + 1, result ^ arr[i]);
    }
}
