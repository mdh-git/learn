package com.mdh.algorithm;

/**
 * 删除整数的k个数字，获得删除后的最小值
 * 例如:541270936
 * 使用贪心算法，每次删除一位数字，保证每次删除后的最小
 * 每次删除的数字 从左到右，如果前一位大于后一位数字，删除，否则比较后面的
 * 如541270936
 * 第一次比较: 5 > 4  删除5  得到:41270936
 * 第二次比较: 4 > 1  删除4  得到:1270936
 * 第三次比较: 1 < 2  比较后面的 2 < 7 比较后面的  7 > 0 删除7  得到: 120936
 * ......
 * 依次得[ 局部最优解 ]，最终得到 [ 全局最优解 ]的思想，就是 [ 贪心算法 ]
 * @author madonghao
 * @date 2018/12/6
 */
public class removeKDigitsDemo {
    public static void main(String[] args) {
        System.out.println(removeKDigits("1593212", 3));
        System.out.println(removeKDigits("30200",1));
        System.out.println(removeKDigits("10", 2));
        System.out.println(removeKDigits("541270936", 3));
    }

    /**
     * 删除整数的k个数字，获得删除后的最小值
     * 两层循环，外层循环基于删除次数（k），内层循环从左到右遍历所有数字。
     * 当遍历到需要删除的数字时，利用字符串的自身方法subString() 把对应数字删除，并重新拼接字符串。
     * 时间复杂度是O（kn）
     * @param num 原整数
     * @param k 删除数量
     * @return
     */
    public static String removeKDigits(String num, int k){
        String newNun = num;
        for(int i = 0 ; i < k; i++) {
            boolean hasCut = false;
            //从左向右遍历，找到比自己右侧数字大的数字并删除
            for(int j = 0, len = newNun.length(); j < len - 1; j++){
                if(newNun.charAt(j) > newNun.charAt(j + 1)){
                    newNun = newNun.substring(0,j) + newNun.substring(j + 1, newNun.length());
                    hasCut = true;
                    break;
                }
            }
            //如果没有找到要删除的数字，则删除最后一个数字
            if(!hasCut){
                newNun = newNun.substring(0, newNun.length() - 1);
            }
            //清除整数左侧的数字0
            newNun = removeZero(newNun);
        }
        //
        if(newNun.length() == 0){
            return "0";
        }
        return newNun;
    }

    private static String removeZero(String num) {
        for(int i = 0, len = num.length(); i < len -1; i++){
            if(num.charAt(0) != '0'){
                break;
            }
            num = num.substring(1,num.length());
        }
        return num;
    }
}
