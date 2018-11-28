package com.mdh;

/**
 * Created by madonghao on 2018/11/20.
 */
public class ConTest {
    public static void main(String[] args) {
        String verCode = "yfl0";
        System.out.println(verCode);
        if(verCode.contains("0")){
            verCode = verCode.replace("0","=");
            System.out.println("1111");
        }
        System.out.println(verCode);
    }
}
