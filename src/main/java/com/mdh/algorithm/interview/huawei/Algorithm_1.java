package com.mdh.algorithm.interview.huawei;

/**
 * 华为OD
 * 完美走位问题  窗口问题
 * 给定一个由 W、A、S、D 四种字符组成的字符串，长度一定是4的倍数
 * 你可以任意连续的一段子串，变成 W、A、S、D 组成的随意状态
 * 目的是让4种字符词频一样
 * 返回需要修改的最短长度
 * leetcode原题：
 *
 * WWWW 变成 WASD 至少需要3长度
 * WWDD 变成 WASD 至少需要2长度
 */
public class Algorithm_1 {

    public static void main(String[] args) {
        String a = "WASD";
        String b = "WWWW";
        String c = "WWDD";
        System.out.println("字符串：" + a + "，最短长度：" + balancedString(a));
        System.out.println("字符串：" + b + "，最短长度：" + balancedString(b));
        System.out.println("字符串：" + c + "，最短长度：" + balancedString(c));



    }

    private static int balancedString(String str) {
        // W A S D 替换成数组
        //[0 1 2 3]
        int length = str.length();
        int[] arr = new int[length];
        int[] cnts = new int[4];
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            arr[i] = c == 'W' ? 0 : (c == 'A' ? 1 : (c == 'S' ? 2 : 3));
            cnts[arr[i]]++;
        }
        printlnArr(arr);
        printlnArr(cnts);

        int ans = length;


        return 0;
    }

    private static void printlnArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.println("");
    }
}
