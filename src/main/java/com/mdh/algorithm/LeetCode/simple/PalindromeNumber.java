package com.mdh.algorithm.LeetCode.simple;

/**
 * 回文数
 * https://leetcode-cn.com/problems/palindrome-number/
 *
 * @Author: madonghao
 * @Date: 2021/6/9 5:59 下午
 */
public class PalindromeNumber {
    public static void main(String[] args) {
        //
        int num = 212;
        boolean result = isPalindrome(num);
        System.out.println(result);
    }

    public static boolean isPalindrome(int val) {
        if(val < 0){
            return false;
        }
        String str = "";
        int num = val;
        while (val > 0){
            str = str + val % 10;
            if(String.valueOf(num).equals(str)){
                return true;
            }
            val = val/10;
        }
        return false;
    }
}
