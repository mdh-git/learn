package com.mdh.algorithm;

/**
 * 实现大整数相加
 * 利用三个数组，分别存放两个加数、求和的结果
 * 把大整数倒序存储，整数的个位存于数组0下标位置，最高位存于数组长度-1下标位置。
 * 创建结果数组，结果数组的最大长度是较大整数的位数+1。
 * 遍历两个数组，从左到右按照对应下标把元素两两相加，
 * 把Result数组的全部元素再次逆序，（首位为零，去掉首位的）
 * @author madonghao
 * @date 2018/11/21
 */
public class BigNumberSumDemo {
    public static void main(String[] args) {
        System.out.println(bigNumberSum("123412341235768867546234","3265324314623"));
    }

    /**
     * 大整数求和
     * @param bigNumberA  大整数A
     * @param bigNumberB  大整数B
     */
    public static String bigNumberSum(String bigNumberA, String bigNumberB) {
        //1.把两个大整数用数组逆序存储，数组长度等于较大整数位数+1
        int maxLength = bigNumberA.length() > bigNumberB.length() ? bigNumberA.length() : bigNumberB.length();
        int[] arrayA = new int[maxLength + 1];
        for(int i = 0; i < bigNumberA.length(); i++){
            arrayA[i] = bigNumberA.charAt(bigNumberA.length() - 1 - i) - '0';
        }
        int[] arrayB = new int[maxLength + 1];
        for(int i = 0; i < bigNumberB.length(); i++){
            arrayB[i] = bigNumberB.charAt(bigNumberB.length() - 1 - i) - '0';
        }
        //2.构建result数组，数组长度等于较大整数位数+1
        int[] result = new int[maxLength + 1];
        //3.遍历数组，按位相加
        for(int i = 0; i < result.length; i++){
            int temp = result[i];
            temp += arrayA[i];
            temp += arrayB[i];
            //判断是否进位
            if(temp >= 10){
                temp = temp - 10;
                result[i+1] = 1;
            }
            result[i] = temp;
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
