package com.mdh.algorithm.KMP;

/**
 * BF算法和RK算法
 * RK算法的实现
 * https://mp.weixin.qq.com/s/67uf7pRxXh7Iwm7MMpqJoA
 *
 * @author madonghao
 * @create 2020-02-26 11:03
 **/
public class RKTest {

    public static void main(String[] args) {
        String str = "aacdesadsdfer";
        String pattern = "adsd";
        System.out.println("第一次出现的位置:" + rabinKarp(str, pattern));
    }

    private static int rabinKarp(String str, String pattern) {
        // 主串的长度
        int strLength = str.length();
        // 模式串的长度
        int patternLength = pattern.length();
        // 计算模式串的hash值
        int patternCode = hash(pattern);
        //计算主串当中第一个和模式串等长的子串hash值
        int strCode = hash(str.substring(0, patternLength));

        //用模式串的hash值和主串的局部hash值比较。
        //如果匹配，则进行精确比较；如果不匹配，计算主串中相邻子串的hash值。
        for (int i = 0; i < strLength - patternLength + 1; i++) {
            if(strCode == patternCode && compareString(i, str, pattern)){
                return i;
            }
            if(i < strLength - patternLength){
                strCode = nextHash(str, strCode, i, patternLength);
            }
        }
        return -1;
    }

    private static int nextHash(String str, int hash, int index, int patternLength) {
        hash -= str.charAt(index) - 'a';
        hash += str.charAt(index + patternLength) - 'a';
        return hash;
    }

    private static boolean compareString(int i, String str, String pattern) {
        String strSub = str.substring(i, i + pattern.length());
        return strSub.equals(pattern);
    }

    private static int hash(String str){
        int hashCode = 0;
        //这里采用最简单的hashcode计算方式：
        //把a当做1，把b当中2，把c当中3.....然后按位相加
        for (int i = 0; i < str.length(); i++) {
            hashCode += str.charAt(i) - 'a';
        }
        return hashCode;
    }
}
