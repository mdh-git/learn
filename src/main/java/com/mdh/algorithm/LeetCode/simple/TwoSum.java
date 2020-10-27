package com.mdh.algorithm.LeetCode.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode算法的第一道题目：
 *
 * 1. 两数之和
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * 第一次提交的代码错误，没认真检查第二遍循环的所取到的值，没有认真看代码，需要反思
 * twoSumOne() 这种方法比较笨，看网上的解决方法使用hashMap，之后补充
 *
 * @author madonghao
 * @create 2019-11-06 13:56
 **/
public class TwoSum {
    public static void main(String[] args) {
        int[] nums = {3, 2, 4};
        int target = 7;
        int[] ints1 = twoSumOne(nums, target);
        System.out.println(ints1[0]+ "," + ints1[1]);
        int[] ints2 = twoSumTwo(nums, target);
        System.out.println(ints2[0]+ "," + ints2[1]);
    }

    /**
     * 循环遍历
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSumOne(int[] nums, int target) {
        for(int i = 0; i < nums.length ; i++){
            for(int j = i+1; j < nums.length; j++){
                if(nums[i] + nums[j] == target){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static int[] twoSumTwo(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length ; i++){
            if(map.containsKey(target - nums[i])){
                return new int[]{map.get(target - nums[i]), i};
            }
            // Map    k      v
            // put  当前值   下标
            map.put(nums[i], i);
        }
        return null;
    }
}
