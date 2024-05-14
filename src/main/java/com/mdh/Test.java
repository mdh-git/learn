package com.mdh;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by madonghao on 2018/9/12.
 */
public class Test {

    private static  final  Pattern pattern = Pattern.compile("[0-9]*");
    private static  final  Pattern pattern1 = Pattern.compile("^\\d+(\\.\\d+)?");
    private static  final  Pattern pattern3 = Pattern.compile("[^x00-xff]*");
    public boolean isNumeric(String str){
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            //language=RegExp
            String s = "^\\\\d+()";
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String str = "10000元X4座";
        String str1 = str.split("X")[0];
        String str2 = str.split("X")[1];
        System.out.println(str1.replaceAll("元",""));
        System.out.println(str2.replaceAll("座",""));
        //{"value":1000000,"key":"4"}
        StringBuffer sb = new StringBuffer("{\"value\":")
                .append(str1.replaceAll("元",""))
                .append(",\"key\":\"")
                .append(str2.replaceAll("座",""))
                .append("\"}");
        //System.out.println(sb.toString());

        String str3 = "国产";
        String str4 = "进口";
        if(str3.indexOf("国产") != -1 ) {
            System.out.println("国产 啊哈哈");
        }

        if(str4.indexOf("进口") != -1 ) {
            System.out.println("进口 啊哈哈");
        }

        String len = "测试被保险人手机号002";
        System.out.println(len.length());


        System.out.println("验证");
        System.out.println("验证11111");

        System.out.println("验证222222");

        System.out.println("验证3333333");
    }


}
