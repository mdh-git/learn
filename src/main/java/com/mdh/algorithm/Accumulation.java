package com.mdh.algorithm;

/**
 * 累加求和
 * 1
 * 1+2
 * 1+2+3
 * 1+2+3+4
 * .
 * .
 * .
 * 1+2+3+4+5+...+n
 * @Author : mdh
 * @Date: 2019/4/22
 */
public class Accumulation {
    public static void main(String[] args) {
        System.out.println(accumulation(6));
        System.out.println(accumulation1(6));
        System.out.println(accumulation2(6));
    }

    /**
     * 每次使用factorial得到累加的数据，每一循环依次累加
     * 第一次:     1
     * 第二次:     1 + 2
     * 第三次:     1 + 2 + 3
     * @param n
     * @return
     */
    public static int accumulation(int n){
        //求和
        int sum = 0;
        //初始值，每一次要相加的值
        int factorial = 0;
        for(int i = 1; i <= n;i++){
            factorial += i;
            sum += factorial;
        }
        return sum;
    }

    /**
     * 在上面accumulation的方法进行改进，每次都计算出当前次需要累加的值，而不是accumulation里面的式子
     * @param n
     * @return
     */
    public static int accumulation1(int n){
        //求和
        int sum = 0;
        for(int i = 1; i <= n;i++){
            sum += (1 + i)*i/2;
        }

        return sum;


    }

    /**
     * 归纳总结,根据数据的格式规律总结
     * 1 + 2 + 3 + ... + n = n * (n + 1)/2
     *                     = (n + n * n)/2
     * 由此展开
     * 第一项: 1 = (1 + 1 * 1)/2
     * 第二项: 1 + 2 = (2 + 2 * 2)/2
     * 第三项: 1 + 2 + 3 = (3 + 3 * 3)/2
     * 第四项: 1 + 2 + 3 + 4 = (4 + 4 * 4)/2
     * ......
     * 第n项: 1 + 2 + 3 + 4 + ... +n = (n + n * n)/2
     *
     * n项的和: (1到n的等差数列求和 + 1到n的平方和)/2
     * 1到n的等差数列求和(跟上面一样):  1 + 2 + 3 +... + n = n * (n + 1)/2
     * 1到n的平方和: 1*1 + 2*2 + 3*3 + ... + n*n = n*(n + 1)*(2n + 1)/6
     * 两个求和公式展开合并计算: n*(n + 1)*(n + 2)/6
     * @param n
     * @return
     */
    public static int accumulation2(int n){
        return n*(n + 1)*(n + 2)/6;
    }

}
