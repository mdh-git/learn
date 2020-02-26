package com.mdh.algorithm.KMP;

/**
 * KMP算法
 *
 * https://mp.weixin.qq.com/s/3gYbmAAFh08BQmT-9quItQ
 * @author madonghao
 * @create 2020-02-26 15:57
 **/
public class KMPTest {

    public static void main(String[] args) {
        String str = "ATGTGAGCTGGTGTGTGCFAA";
        String pattern = "GTGTGCF";
        int index = kmp(str, pattern);
        System.out.println("首次出现的位置:" + index);
    }

    // KMP算法主题逻辑 str是主串  pattern是模式串
    private static int kmp(String str, String pattern) {
        // 预处理,生成next数组
        int[] next = getNexts(pattern);
        System.out.println("next");
        int j = 0;
        // 主循环，遍历主串字符
        for(int i = 0; i < str.length(); i++){
            while (j > 0 && str.charAt(i) != pattern.charAt(j)){
                // 遇到坏字符时，查询next数组并改变模式串的起点
                j = next[j];
            }
            if(str.charAt(i) == pattern.charAt(j)){
                j++;
            }
            if(j == pattern.length()){
                // 匹配成功，返回下标
                return i - pattern.length() + 1;
            }
        }
        return -1;
    }

    private static int[] getNexts(String pattern) {
        int[] next = new int[pattern.length()];
        int j = 0;
        for(int i = 2;i < pattern.length(); i++){
            while (j != 0 && pattern.charAt(j) != pattern.charAt(i-1)){
                //从next[i+1]的求解回溯到 next[j]
                i = next[j];
            }
            if(pattern.charAt(j) == pattern.charAt(i-1)){
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
