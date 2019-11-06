package com.mdh.demo;


/**
 *
 * @author madonghao
 * @date 2018/10/15
 */
public class Test {

    public static void main(String[] args) {
//        String str = "WWFssFFs  ss";
//        int count = 0;
//        for(int j = 0 ; j < str.length(); j ++) {
//            char c = str.charAt(j);
//            if(Character.isLowerCase(c)) {
//                count ++;
//            }
//        }
//        System.out.println(count);

        Integer aNull = new Integer("");
        if(aNull != null && !aNull.equals("")){
            System.out.println(aNull);
        } else {
            System.out.println("错误");
        }


    }

}
