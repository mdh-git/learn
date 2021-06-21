package com.mdh.algorithm.LeetCode.medium;

/**
 * @Author: madonghao
 * @Date: 2021/3/18 2:04 下午
 *
 * https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/
 * 剑指 Offer 11.旋转数组的最小数字
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 * 输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
 * 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
 *
 * 示例 1：
 * 输入：[3,4,5,1,2] 输出：1
 *
 * 示例 2：
 * 输入：[2,2,2,0,1] 输出：0
 *
 */
public class MinArray {

    public static void main(String[] args) {
        int[] numbers1 = {3,4,5,1,2};
        int[] numbers2 = {2,2,2,0,1};
        System.out.println(minArray1(numbers1));
        System.out.println(minArray1(numbers2));
    }

    /**
     * 暴力搜索
     * @param numbers
     * @return
     */
    public static int minArray1(int[] numbers) {
        int length = numbers.length;
        if(length == 1){
            return numbers[0];
        }
        int temp = 0;
        for (int i = 0; i < length;i++) {
            if(i == 0){
                temp = numbers[0];
                continue;
            }
            if(temp > numbers[i] ){
                return numbers[i];
            }
        }
        return temp;
    }

    /**
     * 二分查找
     * @param numbers
     * @return
     */
    public static int minArray2(int[] numbers) {
        int length = numbers.length;
        if(length == 1){
            return numbers[0];
        }
        int temp = 0;
        return temp;
    }

}
