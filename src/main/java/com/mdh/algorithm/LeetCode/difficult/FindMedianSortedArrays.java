package com.mdh.algorithm.LeetCode.difficult;

/**
 * leetcode 第四题
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 */
public class FindMedianSortedArrays {

    public static void main(String[] args) {
        int[] nums1 = {1,2,3,4,5};
        int[] nums2 = {6,8,10};

        int sumLength = nums1.length + nums2.length;
        int middle = sumLength % 2 == 0 ? sumLength / 2 : sumLength / 2 + 1;
        //System.out.println(middle);

        findMedianSortedArrays(nums1, nums2);
    }

    public static Double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;

        int sumLength = length1 + length2;

        int middle = sumLength % 2 == 0 ? sumLength / 2 + 1 : sumLength / 2;

        int[] num3 = new int[middle];

        int one = 0;
        int two = 0;
        int count = 0;

        while (count <= middle) {
            if(one == length1 -1){

            }
            if(nums1[one] < nums2[two]){
                num3[count] = nums1[one];
                if(one >= length1 -1 ){
                    two++;
                } else {
                    one++;
                }
            } else if(nums1[one] > nums2[two]) {
                num3[count] = nums1[two];
                if(two >= length2 - 1){
                    two = length2 - 1;
                    one++;
                } else {
                    two++;
                }
            } else {
                num3[count] = nums1[one];
                count++;
                num3[count] = nums1[two];
            }
            count++;
        }

        int i = sumLength % 2 == 0 ? num3[count] + num3[count - 1] : num3[count];

        System.out.println(i);

        return new Double("2.1");
    }
}
