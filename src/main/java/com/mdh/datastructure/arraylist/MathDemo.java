package com.mdh.datastructure.arraylist;

import java.util.Arrays;

/**
 *
 * @author madonghao
 * @date 2018/11/13
 */
public class MathDemo {

    public static void main(String[] args) {
        int oldLen = 10;
        int newLenOne = oldLen + (oldLen >> 1);
        int newLenTwo = newLenOne + (newLenOne >> 1);
        int newLenThree = newLenTwo + (newLenTwo >> 1);
        System.out.println(newLenOne);
        System.out.println(newLenTwo);
        System.out.println(newLenThree);

        int[] array = new int[] {1, 2, 3};
        array = Arrays.copyOf(array, array.length * 2);
        System.out.println(array[3]);
        System.out.println(array.length);
    }
}
