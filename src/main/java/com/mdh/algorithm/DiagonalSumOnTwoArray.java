package com.mdh.algorithm;

/**
 * 数组对角线元素之和
 * 思路: 遍历  行=列
 * @Author donghao.ma
 * @create 2019/4/23 19:05
 */
public class DiagonalSumOnTwoArray {
    public static void main(String[] args) {
        int[][] arrays = {
                {23, 106, 8, 234},
                {25, 9, 73, 19},
                {56, 25, 67, 137},
                {33, 22, 11, 44},
        };
        System.out.println(arraySum(arrays));
        System.out.println(23+9+67+44);
    }

    private static int arraySum(int[][] arrays) {

        //和
        int sum = 0;

        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                if(i == j){
                    sum += arrays[i][j];
                }
            }
        }
        return sum;
    }
}
