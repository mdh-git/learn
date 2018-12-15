package com.mdh.algorithm;

/**
 * 对removeKDigitsDemo里面removeKDigits方法进行优化
 * 运用了栈的特性，在遍历原整数的数字时，让所有数字一个个入栈，
 * 当某个数字需要删除时，让该数字出栈。
 * 最后，程序把栈中的元素转化为字符串结果。
 *
 * 整数 541270936，k=3 为例：
 * 遍历到数字5，数字5入栈：
 * 原整数: 541270936
 * stack: 5
 * 遍历到数字4，发现栈顶5>4，栈顶5出栈，数字4入栈： 删除5
 * 原整数: 541270936
 * stack: 4
 * 遍历到数字1，发现栈顶4>1，栈顶4出栈，数字1入栈： 删除4
 * 原整数: 541270936
 * stack: 1
 * 继续遍历数字2，数字7，依次入栈。
 * 原整数: 541270936
 * stack: 127
 * 遍历数字0，发现栈顶7>0，栈顶7出栈，数字0入栈： 删除7
 * 原整数: 541270936
 * stack: 120
 * 此时k的次数已经用完，无需再比较，剩下的数字全部入栈：
 * stack: 120936
 *
 * 遍历的时间复杂度是O（n），而后把栈转化为字符串的时间复杂度也是O（n），所以最终的时间复杂度是O（n）。\
 * 利用栈来回溯遍历过的数字以及删除数字，空间复杂度是O（n）。
 * @author madonghao
 * @date 2018/12/6
 */
public class removeKDigitsUpdateDemo {

    public static void main(String[] args) {
        System.out.println(removeKDigits("1593212",3));
        System.out.println(removeKDigits("30200",1));
        System.out.println(removeKDigits("10",2));
        System.out.println(removeKDigits("541270936",3));
    }


    /**
     * 删除整数的k个数字，获得删除后的最小值
     * @param num  原整数
     * @param k  删除数量
     * @return
     */
    public static String removeKDigits(String num, int k){
        //新整数的最终长度 = 原整数长度 - k
        int newLength = num.length() - k;
        //创建一个栈，用于接收所有的数字
        char[] stack = new char[num.length()];
        int top = 0;
        for(int i = 0;i < num.length(); i++){
            //遍历当前数字
            char c = num.charAt(i);
            //当栈顶数字大于遍历到的当前数字，栈顶数字出栈（相当于删除数字）
            while (top > 0 && stack[top - 1] > c && k > 0){
                top -= 1;
                k -= 1;
            }
            //遍历到的当前数字入栈
            stack[top++] = c;
        }
        //
        int offset = 0;
        while (offset < newLength && stack[offset] == '0'){
            offset ++;
        }
        return offset == newLength ? "0": new String(stack, offset, newLength - offset);
    }
}
