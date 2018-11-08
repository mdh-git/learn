package com.mdh.sort;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

/**
 * bitMap 的例子
 * @author madonghao
 * @date 2018/10/25
 */
public class BitMapTest {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 3, 4};
        Set<Integer> set = bitMap(arr);
        System.out.println(set);
    }

    /**
     *  数据是以数组的形式传入
     * @param arr 数组
     * @return
     */
    public static Set<Integer> bitMap(int[] arr) {
        int j = 0;
        //用来把重复的数返回，存在Set里，这样避免返回重复的数。
        Set<Integer> output = new HashSet<Integer>();
        BitSet bitSet = new BitSet(Integer.MAX_VALUE);
        int i = 0;
        while (i < arr.length) {
            int value = arr[i];
            //判断该数是否存在bitSet里
            if(bitSet.get(value)) {
                output.add(value);
            } else {
                bitSet.set(value, true);
            }
            i++;
        }
        return output;
    }
}
