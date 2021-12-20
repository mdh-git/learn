package com.mdh.test;

import java.io.*;

/**
 * @Author: madonghao
 * @Date: 2021/9/17 2:10 下午
 */
public class T_004 {
    public static void main(String[] args) {

        try{
            BufferedReader br = new BufferedReader(new FileReader("C:\\teacher.txt"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
