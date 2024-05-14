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
//        Integer categoryId = 1234;
//        String JD_categoryId = "1234";
//        System.out.println(JD_categoryId.equals(String.valueOf(categoryId)));


        AtomicInteger nextHashCode = new AtomicInteger();

        int HASH_INCREMENT = 0x61c88647;
        System.out.println(HASH_INCREMENT);
        System.out.println(nextHashCode.getAndAdd(HASH_INCREMENT));
        System.out.println(nextHashCode.getAndAdd(HASH_INCREMENT));

        System.out.println("验证11111");
    }


}
