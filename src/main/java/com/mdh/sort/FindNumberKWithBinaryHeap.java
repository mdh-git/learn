package com.mdh.sort;

/**
 * @Author: madonghao
 * @Date: 2019/1/4 11:36
 *
 * 寻找无序数组的第k大元素
 * 方法一:排序法( 时间复杂度O(nlogn) )
 * 方法二:插入法( 时间复杂度O(nk) )
 *    维护一个长度为k的数组A,用于存储k个较大的元素
 *    遍历原数组,每遍历一个元素,和数组A最小的元素比较,
 *    如果小于等于数组A的最小元素,继续遍历,如果大于数组A的最小元素,则插入到数组A中,并把数组A最小的元素删除,保持k个最大的
 *
 * 方法三: 最小堆法
 *     1.构建堆的时间复杂度 O(k)
 *     2.遍历剩余数组的时间复杂度 O(n-k)
 *     3.每次调整堆的时间复杂度 O(logk)
 *     2和3是嵌套关系,1和2,3是并行关系,最坏时间复杂度 O((n-k)logk + k)  k远小于n时: O(nlogk)  空间复杂度 O(1)
 */
public class FindNumberKWithBinaryHeap {

    public static void main(String[] args) {
        int[] array = new int[]{7, 5, 15, 3, 17, 2, 20, 24, 1, 9, 12, 8};
        System.out.println(findNumberK(array, 5));
    }

    /**
     * 寻找第k大的元素
     * @param array 待调整的堆
     * @param k 第几大
     * @return
     */
    public static int findNumberK(int[] array, int k){
        //1.用前k个元素构建小顶堆
        buildHeap(array, k);
        //2.继续遍历数组，和堆顶比较
        for(int i = k; i < array.length; i++){
            if(array[i] > array[0]){
                array[0] = array[i];
                downAdjust(array, 0, k);
            }
        }
        //3.返回堆顶元素
        return array[0];
    }

    /**
     * 构建堆
     * @param array 待调整的堆
     * @param length 堆的有效大小
     */
    private static void buildHeap(int[] array, int length) {
        //从最后一个非叶子节点开始，依次下沉调整
        for(int i = (length - 2) / 2; i >= 0; i--){
            downAdjust(array, i, length);
        }
    }

    /**
     * 下沉调整
     * @param array 待调整的堆
     * @param index 要下沉的节点
     * @param length 堆的有效大小
     */
    private static void downAdjust(int[] array, int index, int length) {
        // temp保存父节点值，用于最后的赋值
        int temp = array[index];
        int childIndex = 2 * index + 1;
        while (childIndex < length){
            // 如果有右孩子，且右孩子小于左孩子的值，则定位到右孩子
            if(childIndex + 1 < length && array[childIndex + 1] < array[childIndex]){
                childIndex++;
            }
            // 如果父节点小于任何一个孩子的值，直接跳出
            if(temp <= array[childIndex]){
                break;
            }
            //无需真正交换，单向赋值即可
            array[index] = array[childIndex];
            index = childIndex;
            childIndex = 2 * childIndex + 1;
        }
        array[index] = temp;
    }

}
