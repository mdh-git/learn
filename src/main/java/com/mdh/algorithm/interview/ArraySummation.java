package com.mdh.algorithm.interview;

import java.util.Arrays;


/**
 * 网易
 * 小红拿到了一个长度为N的数组arr,他准备只进行一次修改
 * 可以将数组中任意一个数arr[i],修改为不大于P的正数(修改后的数必须和原数不同)
 * 并使得所有数之和为X的倍数
 * 小红想知道,一共有多少种不同的修改方案
 * 1 <= N ,N <= 10^5
 * 1 <= arr[i],P <= 10^9
 */
public class ArraySummation {

    public static void main(String[] args) {
        int[] arr =randomArray(6 ,20);
        print(arr);
        int P = 3;
        int X = 2;
        int sum = Arrays.stream(arr).sum();
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            cnt += invokerSum(P, X, arr[i], (sum - arr[i]) % X);
        }
        System.out.println();
        System.out.println(cnt);
    }
    private static void print(int[] ans) {
        for (int i = 0; i < ans.length; i++) {
            System.out.print( " " + ans[i]);
        }
    }
    private static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int)(Math.random() * v) + 1;
        }
        return ans;
    }
    private static int invokerSum(int p, int x, int num, int mod) {
        int cnt = (p / x)  + ( (p % x) >= mod ? 1 : 0) -  (mod == 0 ? 1 : 0);
        return cnt - (( num <= p && num % x == mod) ? 1 : 0);
    }


}
