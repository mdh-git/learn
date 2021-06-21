package com.mdh.algorithm.LeetCode.simple;

/**
 * https://leetcode-cn.com/problems/reverse-integer/
 * 7. 整数反转
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。 假设环境不允许存储 64 位整数（有符号或无符号）。
 *
 * @author madonghao
 * 示例 1：
 *     输入：x = 123 输出：321
 * 示例 2：
 *     输入：x = -123 输出：-321
 * 示例 3：
 *     输入：x = 120 输出：21
 * 示例 4：
 *     输入：x = 0 输出：0
 */
public class IntReverse {
    public static void main(String[] args) {
        int reverse = reverse(123);
        System.out.println(reverse);
    }

    /**
     * int 的最大值 2147483647
     * int 的最小值 -2147483648
     * 比较214748364 + 最后一位的大小,即可判断是否大于最大值
     * @param x
     * @return
     */
    public static int reverse(int x) {
        int res = 0;
        while (x != 0){
            // 每次取末尾数字
            int tep = x%10;
            //判断是否 大于 最大32位整数
            if(res > 214748364 || (res == 214748364 && tep > 7)){
                return 0;
            }
            //判断是否 小于 最小32位整数
            if(res < -214748364 || (res == -214748364 && tep < -8)){
                return 0;
            }
            res = res * 10 + tep;
            x = x/10;
        }
        return res;
    }
}
