package com.mdh.algorithm.sort;

import com.alibaba.fastjson.JSONObject;

/**
 * 选择排序:
 * 选择排序是一种直观简单的排序算法，它每次从待排序的数据元素中选出最小(或者最大)元素存放到序列的起始位置，直到全部待排序的数据元素排完。
 * 注意，选择排序并不是稳定的排序。
 * @Author: madonghao
 * @Date: 2019/3/22 10:05
 */
public class SelectSortTest {

    public static void main(String[] args) {
        int[] array = {1,3,2,45,65,33,12};
        SelectSort(array, array.length);
        for(int num : array){
            System.out.println(num);
        }

        System.out.println("---------------");
        int[] array1 = {1,3,2,4,65,23,12};
        selectionSort(array1);
        System.out.println(JSONObject.toJSONString(array1));
    }

    private static void SelectSort(int[] array,int len) {
        //选择排序的优化
        for(int i = 0; i < len; i++){
            // 做第i趟排序
            int min = i;
            // 选最小的记录
            for(int j = i + 1; j < len; j++){
                if(array[min] > array[j]){
                    //记下目前找到的最小值所在的位置
                    min = j;
                }
            }
            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            if(min != i){
                //交换a[i]和a[k]
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
    }


    private static void SelectSort1(int[] array,int len) {
        // 选择排序的优化
        for (int i = 0; i < len; i++) {
          int min = i;
          for(int j = i + 1; j < len; j++){
              if(array[i] > array[j]){
                  min = j;
              }

          }
        }
    }

    private static void selectionSort(int[] nums) {
        for (int i = 0, n = nums.length; i < n - 1; ++i) {
            int minIndex = i;
            for (int j = i; j < n; ++j) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            swap(nums, minIndex, i);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
