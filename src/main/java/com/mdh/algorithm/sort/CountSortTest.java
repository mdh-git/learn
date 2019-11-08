package com.mdh.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 计数排序
 * https://mp.weixin.qq.com/s/WGqndkwLlzyVOHOdGK7X4Q
 *
 * 适用于待排序的值比较小的
 *
 * 例如：
 * 假定20个随机整数的值如下：整数都位于 0 - 10之间
 * 9，3，5，4，9，1，2，7，8，1，3，6，5，3，4，0，10，9 ，7，9
 * 分布在大小为 10 - 0 + 1 大小的数组中，初始值全部为0，遍历数组，比如一个整数是9，那么数组下标为9的元素加1：
 * 数组下标：       0 1 2 3 4 5 6 7 8 9 10
 * 出现的频次：     1 2 1 3 2 2 1 2 1 4 1
 * 最后排序结果：   0 1 1 2 3 3 3 4 4 5 5 6 7 7 8 9 9 9 9 10
 * @author madonghao
 * @create 2019-11-08 10:31
 **/
public class CountSortTest {

    public int[] countSort(int[] array){
        //1.得到数列的最大值和最小值，并算出差值d
        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++){
            if(array[i] > max){
                max = array[i];
            }
            if(array[i] < min){
                min = array[i];
            }
        }
        //2.创建统计数组并统计对应元素个数
        int d = max -min;
        int[] countArray = new int [d + 1];

        for(int i = 0; i < array.length; i++){
            countArray[array[i] - min]++;
        }

        //3.统计数组做变形，后面的元素等于前面的元素之和
        int sum = 0;
        for (int i = 0; i < countArray.length; i++) {
            sum += countArray[i];
            countArray[i] = sum;
        }

        //4.倒序遍历原始数列，从统计数组找到正确位置，输出到结果数组
        int [] sortedArray = new int[array.length];
        for(int i = array.length - 1; i >= 0;i--) {
            sortedArray[countArray[array [i] -min] -1] =array [i];
            countArray[array[i] -min]--;
        }
        return sortedArray;
    }

    @Test
    public void test(){
        int[] array = new int[]{95, 94, 91, 98, 99, 90, 99, 93, 91, 92};
        int[] result = countSort(array);
        System.out.println(Arrays.toString(result));
    }
}
