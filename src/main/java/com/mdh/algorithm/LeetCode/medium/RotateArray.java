package com.mdh.algorithm.LeetCode.medium;

import com.alibaba.fastjson.JSON;

/**
 * https://leetcode-cn.com/problems/rotate-array/
 * 189. 旋转数组
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 * 进阶：
 * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。 你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？
 *
 * @Author: madonghao
 * @Date: 2021/3/13 6:21 下午
 */
public class RotateArray {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        rotate1(arr, 2);
        System.out.println(JSON.toJSON(arr));
    }


    /**
     * 借助一个新的数组 时间复杂度 O(n)，空间复杂度 O(n)
     * @param numbs 原数组
     * @param k 偏移位置
     */
    public static void rotate1(int[] numbs, int k) {
        int size = numbs.length;
        int offSet;
        int[] number = new int[size];
        for (int i = 0; i < size ;i++) {
            offSet = (i + k)%size;
            number[offSet] = numbs[i];
        }
        for (int i = 0; i < size; i++) {
            numbs[i] = number[i];
        }
    }
}
