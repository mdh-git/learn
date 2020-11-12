package com.mdh.test;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author madonghao
 * @create 2020-11-11 14:57
 **/
public class ScannerTest {

    public static void main(String[] args) {
        System.out.println("请输入数字,回车结束!");
        Scanner s = new Scanner(System.in);
        int i = s.nextInt();
        int f = 0;
        int sum = 0;
        for (int j = 0; j < i; j++) {
            System.out.println("请输入" + (j+1) + "数字,回车结束!");
            Scanner num = new Scanner(System.in);
            int i1 = num.nextInt();
            if(i1 < 0){
                f++;
            } else {
                sum += i1;
            }
        }

        System.out.println("负数的个数：" + f);

        System.out.println("平均值：" + new BigDecimal(sum).divide(new BigDecimal(i), 1, BigDecimal.ROUND_DOWN));
    }
}
