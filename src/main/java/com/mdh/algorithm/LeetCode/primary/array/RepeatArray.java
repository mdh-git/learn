package com.mdh.algorithm.LeetCode.primary.array;

/**
 * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2gy9m/
 * 删除排序数组中的重复项
 *
 * @Author: madonghao
 * @Date: 2021/8/13 11:46 上午
 */
public class RepeatArray {

  /**
   * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
   * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1)
   * 额外空间的条件下完成。
   *
   * 示例 1：
   * 输入：nums = [1,1,2]
   * 输出：2, nums = [1,2]
   * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2。不需要考虑数组中超出新长度后面的元素。
   *
   * 示例 2：
   * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
   * 输出：5, nums = [0,1,2,3,4]
   * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
   *
   * @param nums
   * @return
   */
    public static int removeDuplicates(int[] nums) {

        //边界条件判断
        if(nums == null || nums.length == 0){
            return 0;
        }
        int left = 0;
        for (int right = 1; right < nums.length;) {
            if(nums[left] == nums[right]){
                right ++;
            } else {
                nums[++left] = nums[right];
            }
        }
        return ++left;
    }

    /**
     * 双指针
     * @param nums
     * @return
     */
    public static int removeDuplicates1(int[] nums) {
        //边界条件判断
        if(nums == null || nums.length == 0){
            return 0;
        }
        int left = 0;
        for (int right = 1; right < nums.length; right ++) {
            if(nums[left] != nums[right]){
                nums[++left] = nums[right];
            }
        }
        return ++left;
    }

    /**
     * 判断重复
     * @param nums
     * @return
     */
    public static int removeDuplicates2(int[] nums) {
        //重复的数字个数
        int count = 0;
        for (int right = 1; right < nums.length; right++) {
            if (nums[right] == nums[right - 1]) {
                //如果有重复的，count要加1
                count++;
            } else {
                //如果没有重复，后面的就往前挪
                nums[right - count] = nums[right];
            }
        }
        //数组的长度减去重复的个数
        return nums.length - count;
    }

    public static void main(String[] args) {
        int[] num = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicates(num));
        int[] num1 = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicates1(num1));
        int[] num2 = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicates2(num2));
    }


}
