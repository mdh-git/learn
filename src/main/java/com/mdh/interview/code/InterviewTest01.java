package com.mdh.interview.code;

/**
 *
 * @Author : mdh
 * @Date: 2019/3/16
 */
public class InterviewTest01 {

    public static void main(String[] args) {
        int i = 1;
        i = i++;
        int j = i++;
        int k = i+ ++i * i++;
        System.out.println("i = " + i);
        System.out.println("j = " + j);
        System.out.println("k = " + k);
        // i = 4
        // j = 1
        // k = 11
    }
}
