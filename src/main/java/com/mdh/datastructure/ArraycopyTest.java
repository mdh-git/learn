package com.mdh.datastructure;

/**
 * @author madonghao
 * @create 2020-05-20 16:54
 **/
public class ArraycopyTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int[] a = new int[10];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        a[3] = 3;
        System.arraycopy(a, 2, a, 3, 3);
        a[2]=99;
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
