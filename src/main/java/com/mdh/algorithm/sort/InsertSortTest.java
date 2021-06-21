package com.mdh.algorithm.sort;

/**
 * 插入排序 插入排序基本思想是：每步将一个待排序的纪录，按其关键码值的大小插入前面已经排序的元素序列中适当位置上，直到全部插入完为止。 插入排序是稳定的排序算法。
 *
 * 空间复杂度 O(1)，时间复杂度 O(n²)。
 *
 * 分情况讨论：
 *
 * 1.给定的数组按照顺序排好序：只需要进行 n-1 次比较，两两交换次数为 0，时间复杂度为 O(n)，这是最好的情况。
 * 2.给定的数组按照逆序排列：需要进行 n*(n-1)/2次比较，时间复杂度为 O(n²)，这是最坏的情况。
 * 3.给定的数组杂乱无章：在这种情况下，平均时间复杂度是 O(n²)。
 * 因此，时间复杂度是 O(n²)，这也是一种稳定的排序算法。
 *
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

        System.out.println("----------------");
        int[] array={6,3,8,2,9,1};
        InsertionSort(array, array.length);
        for(int num : arr){
            System.out.println(num);
        }
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

    /**
     * 先来看一个问题。一个有序的数组，我们往里面添加一个新的数据后，如何继续保持数据有序呢？很简单，我们只要遍历数组，找到数据应该插入的位置将其插入即可。
     * 这是一个动态排序的过程，即动态地往有序集合中添加数据，我们可以通过这种方法保持集合中的数据一直有序。而对于一组静态数据，我们也可以借鉴上面讲的插入方法，来进行排序，于是就有了插入排序算法。
     * 那么插入排序具体是如何借助上面的思想来实现排序的呢？
     * 首先，我们将数组中的数据分为两个区间，已排序区间和未排序区间。初始已排序区间只有一个元素，就是数组的第一个元素。插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，并保证已排序区间数据一直有序。重复这个过程，直到未排序区间中元素为空，算法结束。
     *
     * 与冒泡排序对比：
     * 在冒泡排序中，经过每一轮的排序处理后，数组后端的数是排好序的。
     * 在插入排序中，经过每一轮的排序处理后，数组前端的数是排好序的。
     * @param arr
     * @param length
     */
    private static void InsertionSort(int[] arr, int length) {
        for (int i = 1, j; i < length; ++i) {
            int num = arr[i];
            for (j = i - 1; j >=0 && arr[j] > num; --j) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = num;
        }
    }
}
