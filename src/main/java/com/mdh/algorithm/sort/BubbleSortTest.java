package com.mdh.algorithm.sort;

/**
 * 冒泡排序 冒泡排序也是一种直观简单的排序算法，它重复地走访要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。
 * 走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。 冒泡排序是一种稳定的排序。
 *
 * 空间复杂度 O(1)、时间复杂度 O(n²)。
 *
 * 分情况讨论：
 * 1.给定的数组按照顺序已经排好：只需要进行 n-1 次比较，两两交换次数为 0，时间复杂度为 O(n)，这是最好的情况。
 * 2.给定的数组按照逆序排列：需要进行 n*(n-1)/2次比较，时间复杂度为 O(n²)，这是最坏的情况。
 * 3.给定的数组杂乱无章。在这种情况下，平均时间复杂度 O(n²)。
 * 因此，时间复杂度是 O(n²)，这是一种稳定的排序算法。
 *
 * 稳定是指，两个相等的数，在排序过后，相对位置保持不变。
 * @Author: madonghao
 * @Date: 2019/3/22 10:59
 */
public class BubbleSortTest {

    public static void main(String[] args) {
        int[] arr={6,3,8,2,9,1};
        BubbleSort(arr, arr.length);
        BubbleSort1(arr, arr.length);
        for(int num : arr){
            System.out.println(num);
        }
    }

    static void BubbleSort(int[] arr, int length){
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

  /**
   * 定义一个布尔变量 hasChange，用来标记每轮是否进行了交换。在每轮遍历开始时，将 hasChange 设置为 false。
   * 若当轮没有发生交换，说明此时数组已经按照升序排列，hashChange 依然是为 false。此时外层循环直接退出，排序结束。
   * @param arr
   * @param length
   */
  static void BubbleSort1(int[] arr, int length) {
        boolean hasChange = true;
        for(int i = 0; i < length && hasChange; i++){
            hasChange = false;
            for(int j = 0; j < length -i -1; j++){
                if(arr[j] > arr[j+1]){
                    swap(arr, j, j+1);
                    hasChange = true;
                }
            }
        }
    }



    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }


}
