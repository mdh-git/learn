package com.mdh.algorithm;

/**
 * 实现大整数相加
 * 以93281 × 2034 为例子
 *                 9 3 2 8 1
 *     ×            2 0 3 4
 *     -------------------------
 *               3 7 3 1 2 4
 *             2 7 9 8 4 3
 *           0 0 0 0 0 0
 *         1 8 6 5 6 2
 *     -------------------------
 *         1 8 9 7 3 3 5 5 4
 *
 * 利用int型数组,把两个大整数按位进行存储,再把数组中的元素竖式逐个计算
 * 乘法竖式计算:
 * 1.整数B的每一位和整数A所有位数依次相乘,得到中间结果
 * 2.所有中间结果相加,得到最终结果
 *
 * 整数A的长度为m ,整数B的长度为n ,时间复杂度为 O(m*n) = O(n^ 2)
 *
 * 对上面的方法进行修改,可以使用[ 分治法 ]来简化问题的模型
 * 把大整数按照位数拆分成两部分,整数1:  8 1 3 2 5 7 9 0 7 6;整数2:  9 2 1 3 5 2 1 8 4
 * 整数1:  8 1 3 2 5(A)     7 9 0 7 6(B)
 * 整数2:    9 2 1 3(C)     5 2 1 8 4(D)
 * 整数1 = A×10的5次方 + B
 * 整数2 = C×10的5次方 + D
 * 如果把大整数长度抽象为n位
 * 整数1 = A×10的n/2次方 + B
 * 整数2 = C×10的n/2次方 + D
 *    整数1 × 整数2
 *  =(A × 10的n/2次方 + B) × (C × 10的n/2次方 + D)
 *  =AC × 10的n/2次方 + AD × 10的n/2次方 + BC × 10的n/2次方 + BD
 * 原来长度为n的大整数的1次乘积,转化成查长度为n/2的大整数的4次乘积(AC, AD, BC, BD)
 * 这种情况的时间复杂度可以用 [ master定理 ]解释,master定理全称 master theorem,
 * 为许多有 由分治法得到的递推关系式 提供了渐进式复杂度分析。
 *
 * 设常数 a >= 1, b > 1,如果一个算法的整体计算规模T(n) = aT(n/b) + f(n)
 * 具体解析:https://mp.weixin.qq.com/s/cfChrGDIdj5qMk-vVv-6hQ
 * @Author: madonghao
 * @Date: 2018/12/17 19:02
 */
public class BigNumberultiplyDemo {

    public static void main(String[] args) {
        String x = "3338429042340042304302404";
        String y = "12303231";
        System.out.println(multiply(x, y));
    }

    /**
     * 大整数乘积
     * @param bigNumberA 大整数A
     * @param bigNumberB 大整数B
     * @return
     */
    public static String multiply(String bigNumberA, String bigNumberB) {
        //1.把两个大整数用数组逆序存储，数组长度等于两整数长度之和
        int lengthA = bigNumberA.length();
        int lengthB = bigNumberB.length();
        int[] arrayA = new int[lengthA];
        for(int i = 0; i < lengthA; i++){
            arrayA[i] = bigNumberA.charAt(lengthA - 1 - i) - '0';
        }
        int[] arrayB = new int[lengthB];
        for(int i = 0; i < lengthB; i++){
            arrayB[i] = bigNumberB.charAt(lengthB - 1 - i) - '0';
        }
        //2.构建result数组，数组长度等于两整数长度之和
        int[] result = new int[lengthA + lengthB];
        //3.嵌套循环，整数B的每一位依次和整数A的所有数位相乘，并把结果累加
        for(int i = 0; i < lengthB; i++){
            for(int j = 0; j < lengthA; j++){
                //整数B的某一位和整数A的某一位相乘
                result[i + j] += arrayB[i] * arrayA[j];
                //如果result某一位大于10，则进位，进位数量是该位除以10的商
                if(result[i + j] >= 10){
                    result[i + j + 1] += result[i + j] / 10;
                    result[i + j] = result[i + j] % 10;
                }
            }
        }
        //4.把result数组再次逆序并转成String
        StringBuffer sb = new StringBuffer();
        //是否找到大整数的最高有效位
        boolean findFirst = false;
        for(int i = result.length - 1; i >= 0; i--){
            if(!findFirst){
                if(result[i] == 0){
                    continue;
                }
                findFirst = true;
            }
            sb.append(result[i]);
        }
        return sb.toString();
    }
}
