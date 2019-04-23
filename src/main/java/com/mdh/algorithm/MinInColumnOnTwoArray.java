package com.mdh.algorithm;

import java.util.Arrays;

/**
 * 获取二维数组每列最小的值
 * 思路：遍历列，再遍历列中行
 *
 * 一般操作数组都是从行开始，再到列的。
 * 题目是每列的最小值，因此需要在内部for循环遍历的是行
 *
 * @Author donghao.ma
 * @create 2019/4/23 18:40
 */
public class MinInColumnOnTwoArray {
    public static void main(String[] args) {
        int [][] arrays = {
                {23, 106, 8, 234},
                {25, 9, 73, 19},
                {56, 25, 67, 137}
        };
        int[] minArray = minArray(arrays);
        System.out.println(Arrays.toString(minArray));
    }

    /**
     * 求出二维数组每列的最小值()
     * @param array
     * @return
     */
    private static int[] minArray(int[][] array) {

        //获取列数
        int maxColLength = array[0].length;

        //使用一个数组来装载每列最小的值
        int [] minArray = new int[maxColLength];

        //控制列数
        for(int i = 0; i < maxColLength; i++){

            //假设每列的第一个元素是最小的
            int min = array[0][i];

            //控制行数
            for (int j = 0; j < array.length; j++) {

                //找到最小值
                if(min > array[j][i]){
                    min = array[j][i];
                }
            }

            //赋值给装载每列最小的值的数组
            minArray[i] = min;
        }
        return minArray;
    }
}
