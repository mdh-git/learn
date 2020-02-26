package com.mdh.algorithm.KMP;

/**
 * 查找字符串--BM算法
 *
 * https://mp.weixin.qq.com/s/2RlyDBo-Ql-1Ofh8tMyikg
 * @author madonghao
 * @create 2020-02-26 13:59
 **/
public class BMTest {

    public static void main(String[] args) {
        // 主串
        String str = "GTTATAGCTGGTAGCGGCGAA";
        // 模式串
        String pattern = "GTAGCGGCG";
        int index = boyerMoore(str, pattern);
        System.out.println("首次出现位置：" + index);
    }

    private static int boyerMoore(String str, String pattern) {
        int strLength = str.length();
        int patternLength = pattern.length();
        // 模式串的起始位置
        int start = 0;
        while (start <= strLength - patternLength){
            int i;
            // 从后向前,逐个字符比较
            for(i = patternLength -1; i >= 0; i--){
                if(str.charAt(start + i) != pattern.charAt(i)){
                    // 发现坏字符,跳出比较
                    break;
                }
            }
            if(i < 0){
                // 匹配成功,返回第一次匹配的下标位置
                return start;
            }
            // 寻找坏字符在模式串中的对应
            int charIndex = findCharacter(pattern, str.charAt(start+i), i);
            // 计算怀字符产生的位移
            int bcOffset = charIndex >= 0 ? i - charIndex : i + 1;
            start += bcOffset;
        }
        return -1;
    }

    // 在模式串中，查找index下标之前的字符是否和坏字符匹配
    private static int findCharacter(String pattern, char badCharacter, int index) {
        for(int i = index-1; i>= 0; i--){
            if(pattern.charAt(i) == badCharacter){
                return i;
            }
        }
        // 模式串不存在该字符,返回-1
        return -1;
    }
}
