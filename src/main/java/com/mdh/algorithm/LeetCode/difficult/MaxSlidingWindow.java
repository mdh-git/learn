package com.mdh.algorithm.LeetCode.difficult;

import java.util.Arrays;

/**
 * 剑指 Offer 59 - I. 滑动窗口的最大值
 *
 * @Author: madonghao
 * @Date: 2021/7/22 7:46 下午
 */
public class MaxSlidingWindow {


    public static void main(String[] args) {

        int[] arr = {1,3,-1,-3,5,3,6,7};
        int size = 0;
        int[] ints = maxSlidingWindow(arr, size);
        System.out.println(Arrays.toString(ints));
    }


    public static int[] maxSlidingWindow(int[] nums, int k) {
        int length = nums.length;
        int[] result = new int[1];
        if(k <= 1){
            return nums;
        }

        if(length <= k){
            result[0] = maxArray(nums);
            return result;
        }
        
        result = new int[length - k + 1];

        for (int i = 0; i < length - k + 1; i++) {
            int sp = (i + k ) <= length ? i + k : length;
            int[] ints = Arrays.copyOfRange(nums, i, sp);
            if(ints.length > 0){
                int max = maxArray(ints);
                result[i] = max;
            }
        }

        return result;
    }

    public static int maxArray(int[] nums) {
        if(nums.length == 1 ){
            return nums[0];
        }
        int i = nums[0];
        for (int j = 0; j < nums.length; j++) {
            if(nums[j] > i){
                i = nums[j];
            }
        }
        return i;
    }
}
