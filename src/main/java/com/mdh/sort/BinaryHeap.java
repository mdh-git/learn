package com.mdh.sort;

import java.util.Arrays;

/**
 * 二叉堆
 * @author madonghao
 * @date 2018/10/24
 */
public class BinaryHeap {

    /**
     * 上浮操作，对插入的节点进行上浮
     * @param arr 数组
     * @param length 表示二叉堆的长度
     * @return
     */
    public static int[] upAdjust(int arr[], int length) {
        //标记插入的节点
        int child = length - 1;
        //其父亲节点
        int parent = (child - 1) / 2;
        //把插入的节点临时保存起来
        int temp = arr[child];

        //进行上浮
        while (child > 0 && temp < arr[parent]) {
            //其实不用进行每次都进行交换，单向赋值就可以了
            //当temp找到正确的位置之后，我们再把temp的值赋给这个节点
            arr[child] = arr[parent];
            child = parent;
            parent = (child - 1) / 2;
        }
        //退出循环代表找到正确的位置
        arr[child] = temp;
        return arr;
    }

    /**
     *  下沉操作，执行删除操作相当于把最后
     *  一个元素赋给根元素之后，然后对根元素执行下沉操作
     * @param arr 数组
     * @param parent 要下沉元素的下标
     * @param length 数组长度
     * @return
     */
    public static int[] downAdjust(int[] arr, int parent, int length) {
        //临时保证要下沉的元素
        int temp = arr[parent];
        //定位左孩子节点位置
        int child = 2 * parent + 1;
        //开始下沉
        while (child < length) {
            //如果右孩子节点比左孩子小，则定位到右孩子
            if(child + 1 < length && arr[child] > arr[child + 1]) {
                child++;
            }
            //如果父节点比孩子节点小或等于，则下沉结束
            if(temp <= arr[child]) {
                break;
            }
            //单向赋值
            arr[parent] = arr[child];
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = temp;
        return arr;
    }

    /**
     * 构建数组
     * @param arr 数组
     * @param length 数组的长度
     * @return
     */
    public static int[] buildHead(int[] arr, int length) {
        for(int i = (length - 2) / 2; i >= 0; i--) {
            arr = downAdjust(arr, i, length);
        }
        return arr;
    }

    //测试用例
    public static void main(String[] args) {
        int[] arr = new int[]{2, 5, 7, 1, 4, 9, 8};
        System.out.println("开始:" + Arrays.toString(arr));
        System.out.println("结束:" + Arrays.toString(buildHead(arr, 7)));
    }
}
