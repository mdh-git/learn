package com.mdh.algorithm.sort;

/**
 * 插入排序
 * 插入排序基本思想是：每步将一个待排序的纪录，按其关键码值的大小插入前面已经排序的元素序列中适当位置上，直到全部插入完为止。插入排序是稳定的排序算法。
 * @Author: madonghao
 * @Date: 2019/3/22 11:22
 */
public class InsertSortTest {

    public static void main(String[] args) {
        int[] arr={6,3,8,2,9,1};
        InsertSort(arr, arr.length);
        for(int num : arr){
            System.out.println(num);
        }

        int[] array={6,3,8,2,9,1};
        InsertSort2(array, array.length);
    }

    private static void InsertSort(int[] arr, int length) {
        for(int i = 0; i < length; i++){
            for(int j = i; j > 0 && arr[j -1] > arr[j];j--){
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
        }
    }

    private static void InsertSort2(int[] arr, int length) {
    }
}
