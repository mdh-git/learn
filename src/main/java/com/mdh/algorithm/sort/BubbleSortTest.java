package com.mdh.algorithm.sort;

/**
 * 冒泡排序
 * 冒泡排序也是一种直观简单的排序算法，它重复地走访要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。冒泡排序是一种稳定的排序。
 * @Author: madonghao
 * @Date: 2019/3/22 10:59
 */
public class BubbleSortTest {

    public static void main(String[] args) {
        int[] arr={6,3,8,2,9,1};
        BubbleSort(arr, arr.length);
        for(int num : arr){
            System.out.println(num);
        }
    }

    static void BubbleSort(int arr[] , int length){
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length -i -1; j++){
                if(arr[j] >  arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
}
