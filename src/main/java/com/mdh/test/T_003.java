package com.mdh.test;

import java.io.*;

/**
 * @Author: madonghao
 * @Date: 2021/9/17 2:09 下午
 */
public class T_003 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\teacher.txt"));
        System.out.println(br.readLine());
    }
}
