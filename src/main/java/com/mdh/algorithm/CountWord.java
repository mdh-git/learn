package com.mdh.algorithm;

/**
 * 计算单词的个数:
 * 输入一段字符，计算出里面单词的个数，单词之间用空格隔开 ,一个空格隔开，就代表着一个单词了
 *
 * 思路:
 * 把字符遍历一遍，累计由空格串转换为非空格串的次数，次数就是单词的个数
 * 定义一个标志性变量flag，0表示的是空格状态，1表示的是非空格状态
 * @Author donghao.ma
 * @create 2019/4/23 23:08
 */
public class CountWord {
    public static void main(String[] args) {
        String str = "a   b";
        System.out.println(countWord(str));
    }

    /**
     * 输入一段字符，计算出里面单词的个数
     *
     * @param str 一段文字
     */
    public static int countWord(String str) {

        // 0 表示空格状态，1 表示非空格状态
        int flag = 0;

        // 单词次数
        int num = 0;

        for (int i = 0; i < str.length(); i++) {

            if (String.valueOf(str.charAt(i)).equals(" ") ) {
                flag = 0;
            } else if (flag == 0) {
                num++;
                flag = 1;
            }

        }

        return num ;

    }
}
