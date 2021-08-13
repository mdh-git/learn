package com.mdh.algorithm.LeetCode.primary.array;

/**
 * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2skh7/
 * 旋转数组
 *
 * @Author: madonghao
 * @Date: 2021/8/13 3:52 下午
 */
public class RotateArray {

    /**
     * 示例 1:
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释: 向右旋转 1 步: [7,1,2,3,4,5,6]
     *      向右旋转 2 步: [6,7,1,2,3,4,5]
     *      向右旋转 3 步: [5,6,7,1,2,3,4]
     *
     * 示例 2:
     * 输入：nums = [-1,-100,3,99], k = 2
     * 输出：[3,99,-1,-100]
     * 解释: 向右旋转 1 步: [99,-1,-100,3]
     *      向右旋转 2 步:  [3,99,-1,-100]
     *
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {
        int length = nums.length;
        int move = length - (k % length);

        int[] newNums = new int[length];
        for (int i = 0; i < length; i++){
            int i2 = i + move;
            int i1 = i2  >= length ? (i2 - length ): i2;
            newNums[i] = nums[i1];
        }
        for (int i = 0; i < length; i++) {
            nums[i] = newNums[i];
        }
    }

    public static void rotate1(int[] nums, int k) {
        int length = nums.length;
        int temp[] = new int[length];

        for (int i = 0; i < length; i++) {
            temp[i] = nums[i];
        }

        for (int i = 0; i < length; i++) {
            nums[(i + k) % length] = temp[i];
        }
    }



    public static void main(String[] args) {
        int[] num = {1,2,3,4,5,6,7};
        int k = 3;
        rotate1(num, k);
        for (int i : num) {
            System.out.print(i + "\t");
        }
    }
}
