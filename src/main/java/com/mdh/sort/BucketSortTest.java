package com.mdh.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 桶排序
 * @author madonghao
 * @date 2018/10/15
 */
public class BucketSortTest {

    public static void main(String[] args) {
        double[] array = new double[]{4.12, 6.421, 0.5, 3.0, 2.12,5.5,6.5,7.5,6.678,9.5};
        double[] sortArray = bucketSort(array);
        System.out.println(Arrays.toString(sortArray));
    }

    public static double[] bucketSort(double[] array) {

        //1.得到数列的最大值和最小值，并算出差值difference
        double max = array[0];
        double min = array[0];
        for(int i = 0, len = array.length; i < len; i++){
            if(max < array[i]) {
                max = array[i];
            }
            if (min > array[i]) {
                min = array[i];
            }
        }

        double difference = max - min;

        //2.初始化桶
        int bucketNum = array.length;
        ArrayList<LinkedList<Double>> bucketList = new ArrayList<LinkedList<Double>>(bucketNum);
        for(int i = 0; i < bucketNum ; i++) {
            bucketList.add(new LinkedList<Double>());
        }

        //3.遍历原始数组，将每个元素放入桶中
        for(int i = 0;i < array.length;i++){
            int num = (int) ((array[i] - min)*(bucketNum - 1)/difference);
            System.out.println(num);
            bucketList.get(num).add(array[i]);
        }

        //4.对每个通内部进行排序
        for(int i = 0,len = bucketList.size();i<len;i++){
            //JDK底层采用了归并排序或归并的优化版本
            Collections.sort(bucketList.get(i));
        }

        //5.输出全部元素
        double[] sortArray = new double[array.length];
        int index = 0;
        for(LinkedList<Double> list: bucketList) {
            for(double element: list){
                sortArray[index] = element;
                index ++;
            }
        }
        return sortArray;
    }
}
