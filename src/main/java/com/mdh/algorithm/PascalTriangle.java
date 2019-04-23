package com.mdh.algorithm;

import java.util.Scanner;

/**
 * 打印杨辉三角形
 * 效果:
 *  1
 *  1  1
 *  1  2  1
 *  1  3  3   1
 *  1  4  6   4   1
 *  1  5  10  10  5    1
 *  1  6  15  20  15   6    1
 *  1  7  21  35  35   21   8    1
 *  1  8  28  56  70   56   36   9   1
 *  1  9  36  84  126  126  120  45  10  1
 *
 *  规律:
 *  每行的第一个和最后一个都是1
 *      (进一步推算：第1列全部为1，第一行全都是1，当列数等于行数为1)
 *  当前值等于头上的值加头上的左边的值
 * @Author donghao.ma
 * @create 2019/4/23 19:28
 */
public class PascalTriangle {
    public static void main(String[] args) {

        //从控制台获取行数
        System.out.println("请输入行数,回车结束!");
        Scanner s = new Scanner(System.in);
        int row = s.nextInt();
        //根据行数定义好二维数组，由于每一行的元素个数不同，所以不定义每一行的个数
        int[][] arr = new int[row][];

        //遍历二维数组
        for (int i = 0; i < arr.length; i++) {
            //初始化每一行的这个一维数组
            arr[i] = new int[i + 1];
            //遍历这个一维数组，添加元素
            for (int j = 0; j <= i; j++) {
                //每一列的开头和结尾元素为1，开头的时候，j=0，结尾的时候，j=i
                if(j==0 || i==j){
                    arr[i][j] = 1;
                } else {
                    //每一个元素是它上一行的元素和斜对角元素之和
                    arr[i][j] = arr[i-1][j-1] + arr[i-1][j];
                }
                System.out.print(arr[i][j] + "\t\t");
            }
            System.out.println();
        }
    }
}
