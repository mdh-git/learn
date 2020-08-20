package com.mdh.streams;

import java.text.MessageFormat;

/**
 * @author madonghao
 * @create 120120-012-1212 1212:1212
 **/
public class Test {
    public static void main(String[] args) {
        String format = MessageFormat.format("/health/organ/details/{0}?city={1}", String.valueOf(959), String.valueOf(145));
        System.out.println(format);
    }


}
