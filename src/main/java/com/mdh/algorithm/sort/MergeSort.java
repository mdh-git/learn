package com.mdh.algorithm.sort;

import java.util.Arrays;

/**
 * @Author: madonghao
 * @Date: 2021/4/29 7:44 下午
 */
public class MergeSort {

    public static void main(String[] args) {

        int[] nums = {1, 2, 7, 4, 5, 3, 9, 8};
        mergeSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    private static void mergeSort(int[] nums) {
        int length = nums.length;
        int[] temp = new int[length];
        mergeSort(nums, 0, length - 1, temp);
    }

    private static void mergeSort(int[] nums, int low, int high, int[] temp) {
        if(low >= high){
            return;
        }
        int mid = (low + high) >>> 1;
        mergeSort(nums, 0, mid, temp);
        mergeSort(nums, mid + 1, high, temp);
        merge(nums, low, mid, high, temp);
    }

    private static void merge(int[] nums, int low, int mid, int high, int[] temp) {
        int i = low, j = mid + 1, k = low;
        while (k <= high) {
            if (i > mid) {
                temp[k++] = nums[j++];
            } else if (j > high) {
                temp[k++] = nums[i++];
            } else if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        System.arraycopy(temp, low, nums, low, high - low + 1);
    }


}
