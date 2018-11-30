package com.mdh;

/**
 *
 * @author madonghao
 * @date 2018/11/14
 */
public class MathDemo {

    public static void main(String[] args) {

        String code = "0f01";
        if(code.contains("0")){
            System.out.println("包含0");
            code = code.replaceAll("0", "o");
            System.out.println(code);
        } else {
            System.out.println("不含0");
        }
    }

}
