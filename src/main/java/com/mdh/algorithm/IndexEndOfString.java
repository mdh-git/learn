package com.mdh.algorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESCRIPTION: 求开头结束包含某个字符串
 * 已知一个字符串s，内容为随机字母组合，比如"abcdabcd"
 * 给定start，end两个字符串，比如start = "ab" end = "cd"
 * 要求给出所有以start开头，end结束的子串
 * ["abcd", "abcdabcd", "abcd"]
 * @author donghao.ma
 * @date 2019/5/24 10:40
 */
public class IndexEndOfString {

    public static void main(String[] args) {
        String s = "hhhab1cdab2cdhhhab3cdab4cd";
        String start = "ab";
        String end = "cd";
        Pattern p = Pattern.compile(".?(" + start +")?.*?" + end);
        Matcher m = p.matcher(s);
        while (m.find()) {
            System.out.println(m.group());
        }


    }
}
