package com.mdh.collection.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

/**
 * ArrayUtils工具类的操作
 * https://www.cnblogs.com/ooo0/p/9246750.html
 *
 * @author madonghao
 * @create 2020-03-18 17:53
 **/
public class ArrayUtilsTest {

    @Test
    public void test(){
    }

    @Test
    public void test01(){
        // 1.add():将给定的数据添加到指定的数组中，返回一个新的数组。
        int[] arr = { 1, 2, 3 };
        int[] newArr = ArrayUtils.add(arr, 4); // new新数组长度比旧数组大1,copy旧数组到新数组,把元素赋值到最后一位
        System.out.println(ArrayUtils.toString(newArr)); // {1,2,3,4}
    }

    @Test
    public void test02(){
        // 2.addAll():合并两个数组。 不会对重复的去重
        int[] arr = { 1, 2, 3 };
        int[] arr2 = { 3, 5, 6 };
        int[] newArr = ArrayUtils.addAll(arr, arr2);
        System.out.println(ArrayUtils.toString(newArr)); // {1,2,3,4,5,6}
    }

    @Test
    public void test03(){
        // 3.contains():检查该数据在该数组中是否存在，返回一个boolean值。
        int[] arr = { 1, 2, 3 };
        boolean contains = ArrayUtils.contains(arr, 2); // 遍历判断相等
        System.out.println(contains); // true
    }

    @Test
    public void test04(){
        // 4.getLength():返回该数组长度。
        int[] arr = { 1, 2, 3 };
        int length = ArrayUtils.getLength(arr); // 调用Array.getLength(arr)
        System.out.println(length); // 3
        System.out.println(arr.length); // 3
    }

    @Test
    public void test05(){
        // 5.indexOf():从数组的第一位开始查询该数组中是否有指定的数值，存在返回index的数值，否则返回-1。
        int[] arr = { 1, 2, 3 };
        int indexOf = ArrayUtils.indexOf(arr, 3); // 遍历判断相等
        System.out.println(indexOf); // 2
    }

    @Test
    public void test06(){
        int[] arr = { 1, 2, 3 , 2, 2};
        // 6.lastIndexOf():从数组的最后一位开始往前查询该数组中是否有指定的数值，存在返回index的数值，否则返回-1。
        int lastIndexOf = ArrayUtils.lastIndexOf(arr, 2); // 遍历判断相等
        System.out.println(lastIndexOf); // 4
    }

    @Test
    public void test07(){
        // 7.Insert():向指定的位置往该数组添加指定的元素，返回一个新的数组。
        int[] arr = { 1, 3 , 5};
        int[] arr2 = { 2, 4 , 6};
        int[] newArr = ArrayUtils.insert(2, arr, arr2); // new新数组长度是源数组和目标数组的长度和,再把源数组和目标数组arraycopy到新数组
        System.out.println(ArrayUtils.toString(newArr)); // {1,3,2,4,6,5}
    }

    @Test
    public void test08(){
        // 8.isEmpty():判断该数组是否为空，返回一个boolean值。
        int[] arr = { 1, 3 , 5};
        boolean isEmpty = ArrayUtils.isEmpty(arr); // 调用getLength(array) == 0;
        System.out.println(isEmpty); // false

        int[] arr3 = { 1, 2, 3 };
        boolean isEquals = ArrayUtils.isEquals(arr, arr3); // 不推荐使用,建议使用Arrays.equals(arr, arr3)
        System.out.println(isEquals); // false
        boolean equals = Arrays.equals(arr, arr3);
        System.out.println(equals); // false
    }

    @Test
    public void test09(){
        // 9.isNotEmpty():判断该数组是否为空，而不是null。
        int[] arr = { 1, 3 , 5};
        boolean isNotEmpty = ArrayUtils.isNotEmpty(arr);
        System.out.println(isNotEmpty); // true
    }

    @Test
    public void test10(){
        // 10.isSameLength():判断两个数组的长度是否一样，当数组为空视长度为0。返回一个boolean值。
        int[] arr = { 1, 3 , 5};
        int[] arr3 = { 1, 3 , 5, 4};
        boolean isSameLength = ArrayUtils.isSameLength(arr, arr3); // 调用getLength(arr) == getLength(arr3)
        System.out.println(isSameLength); // false
    }

    @Test
    public void test11(){
        // 11.isSameType():判断两个数组的类型是否一样，返回一个boolean值。
        int[] arr = { 1, 3 , 5};
        String[] arr3 = { "1", "3", "5"};
        boolean isSameType = ArrayUtils.isSameType(arr, arr3); // 调用array1.getClass().getName().equals(array2.getClass().getName())
        System.out.println(isSameType); // false
    }

    @Test
    public void test12(){
        // 12.isSorted():判断该数组是否按照自然排列顺序排序，返回一个boolean值。
        int[] arr = { 1, 6 , 5};
        boolean isSorted = ArrayUtils.isSorted(arr); // 使用Comparator实现
        System.out.println(isSorted); // false
    }

    @Test
    public void test13(){
        // 13.nullToEmpty():
        int[] nullArr = null;
        int[] nullToEmpty = ArrayUtils.nullToEmpty(nullArr); // 如果arr为null,返回new int[0]
        System.out.println(ArrayUtils.toString(nullToEmpty)); // {}
    }

    @Test
    public void test14(){
        // 14.remove():删除该数组指定位置上的元素，返回一个新的数组。
        int[] arr = { 1, 6 , 5};
        int[] newArr = ArrayUtils.remove(arr, 0);
        System.out.println(ArrayUtils.toString(newArr)); // {6,5}
    }

    @Test
    public void test15(){
        // 15.removeAll():删除指定位置上的元素，返回一个新的数组。
        int[] arr = { 1, 6 , 5};
        int[] newArr = ArrayUtils.removeAll(arr, 0, 1);
        System.out.println(ArrayUtils.toString(newArr)); // {5}
    }

    @Test
    public void test16(){
        // 16.removeAllOccurences():从该数组中删除指定的元素，返回一个新的数组。
        int[] arr = { 1, 6 , 5};
        int[] newArr = ArrayUtils.removeAllOccurences(arr, 6); // 遍历使用indexOf()找出所有指定元素,在执行removeAll()
        System.out.println(ArrayUtils.toString(newArr)); // {1,5}
    }

    @Test
    public void test17(){
        // 17.removeElement():从该数组中删除第一次出现的指定元素，返回一个新的数组。
        int[] arr = { 1, 6 , 5, 4, 5};
        int[] newArr = ArrayUtils.removeElement(arr, 5); // 使用indexOf()找出第一次出现的指定元素,在执行remove()
        System.out.println(ArrayUtils.toString(newArr)); // {1,6,4,5}
    }

    @Test
    public void test18(){
        // 18.removeElements():从该数组中删除指定数量的元素，返回一个新的数组。
        int[] arr = { 1, 6 , 5, 4, 5};
        int[] newArr = ArrayUtils.removeElements(arr, 1, 5);
        System.out.println(ArrayUtils.toString(newArr)); // {6,4,5}
    }

    @Test
    public void test19(){
        // 19.reverse():数组反转。也可以指定开始和结束的反转位置。
        int[] arr = { 1, 6 , 5, 4, 5};
        ArrayUtils.reverse(arr); //  首尾对称交换
        System.out.println(ArrayUtils.toString(arr)); // {5,4,5,6,1}

        // 移位
        ArrayUtils.shift(arr, 2);
        System.out.println(ArrayUtils.toString(arr)); // {6,1,5,4,5}

        // 打乱
        ArrayUtils.shuffle(arr);
        System.out.println(ArrayUtils.toString(arr));
    }

    @Test
    public void test20(){
        // 20.subarray():截取数组（包头不包尾），返回一个新的数组
        int[] arr = { 1, 6 , 5, 4, 5};
        int[] subarray = ArrayUtils.subarray(arr, 0, 2);
        System.out.println(ArrayUtils.toString(subarray)); // {1,6}

    }

    @Test
    public void test21(){
        // 21.swap():指定该数组的两个位置的元素交换或者指定两个位置后加len的长度元素进行交换。
        int[] arr = { 1, 6 , 5, 4, 5};
        ArrayUtils.swap(arr, 0, 1);
        System.out.println(ArrayUtils.toString(arr)); // {6,1,5,4,5}
    }

    @Test
    public void test22(){
        // 22.toMap():将数组转换成Map,返回一个map的Object的集合。
        String[][] strArr = { { "RED", "#FF0000" }, { "GREEN", "#00FF00" }, { "BLUE", "#0000FF" } };
        Map colorMap = ArrayUtils.toMap(strArr);
        System.out.println(colorMap);
    }

    @Test
    public void test23(){
        // 23.toObject():将原始数据类型的数组转换成包装类数组。
        int[] arr = { 1, 6 , 5, 4, 5};
        ArrayUtils.toObject(arr); // 遍历转换
        System.out.println(ArrayUtils.toString(arr)); // Integer类型
    }

    @Test
    public void test24(){
        // 24.toPrimitive():将包装类数组转换成原始数据类型数组。
        int[] arr = { 1, 6 , 5, 4, 5};
        ArrayUtils.toPrimitive(arr);
        System.out.println(ArrayUtils.toString(arr)); // int类型
    }

    @Test
    public void test25(){
        // 25.toString():将数组输出为Stirng,返回一个字符串。
        int[] arr = { 1, 6 , 5, 4, 5};
        String str = ArrayUtils.toString(arr);
        System.out.println(str); // {1,6,5,4,5}
    }

    @Test
    public void test26(){
        // 26.toStringArray():将Object数组转换为String数组类型。
        String[] strArr2 = ArrayUtils.toStringArray(new Object[]{ "1", "2" });
        System.out.println(ArrayUtils.toString(strArr2)); // {1,2}
    }
}
