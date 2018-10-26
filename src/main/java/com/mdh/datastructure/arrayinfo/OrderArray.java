package com.mdh.datastructure.arrayinfo;

import java.util.Arrays;

/**
 * 有序数组
 * 所谓的有序数组就是指数组中的元素是按一定规则排列的，其好处就是在根据元素值查找时可以是使用二分查找，
 * 查找效率要比无序数组高很多，在数据量很大时更加明显。
 * 当然缺点也显而易见，当插入一个元素时，首先要判断该元素应该插入的下标，
 * 然后对该下标之后的所有元素后移一位，才能进行插入，这无疑增加了很大的开销。
 *
 * 优点:查找效率高
 * 缺点:删除和插入慢，大小固定
 *
 * @author madonghao
 * @date 2018/10/26
 */
public class OrderArray {
    private int[] intArray;
    //数组元素个数
    private int length = 0;

    /**
     * 构造方法，传入数组最大长度
     * @param max
     */
    public OrderArray(int max) {
        intArray = new int[max];
    }

    /**
     * 用二分查找法定位某个元素，如果存在返回其下标，不存在则返回-1
     * @param target
     * @return
     */
    public int find(int target) {
        //搜索段最小元素的小标
        int lowerBound = 0;
        //搜索段最大元素的下标
        int upperBound = length - 1;
        //当前检测元素的下标
        int curIn;

        //如果数组为空，直接返回-1
        if (upperBound < 0) {
            return -1;
        }
        while (true) {
            curIn = (lowerBound + upperBound) / 2;
            if(target == intArray[curIn]) {
                return curIn;
            } else if (curIn == lowerBound){
                // 在当前下标与搜索段的最小下标重合时，代表搜索段中只包含1个或2个元素
                // 既然走到该分支，证明上一个if分支不满足，即目标元素不等于低位元素
                if (target == intArray[upperBound]) {
                    return upperBound;
                } else {
                    // 高位元素也不等于目标元素，证明数组中没有该元素，搜索结束
                    return -1;
                }
            } else {
                // 搜索段中的元素至少有三个，且当前元素不等于目标元素
                if (intArray[curIn] < target) {
                    // 如果当前元素小于目标元素，则将下一个搜索段的最小下标置为当前元素的下标
                    lowerBound = curIn;
                }else{
                    // 如果当前元素大于目标元素，则将下一个搜索段的最大下标置为当前元素的下标
                    upperBound = curIn;
                }
            }
        }
    }

    /**
     * 插入
     * @param elem 插入的元素
     */
    public void insert(int elem) {
        int location = 0;

        // 判断应插入位置的下标
        for(;location < length; location++) {
            if(intArray[location] > elem) {
                break;
            }
        }
        System.out.println("location:" + location);
        // 将插入下标之后的所有元素后移一位
        for(int i = length; i > location; i--) {
            intArray[i] = intArray[i - 1];
        }

        intArray[location] = elem;

        length++;
    }

    /**
     * 删除某个指定的元素值，删除成功则返回true，否则返回false
     * @param target
     * @return
     */
    public boolean delete(int target) {
        int index = -1;
        if((index = find(target)) != -1) {
            for(int i = index; i < length - 1; i++) {
                //删除元素之后的所有元素前移一位
                intArray[i] = intArray[i + 1];
            }
            length --;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 列出所有元素
     */
    public void display(){
        for(int i = 0; i < length; i++) {
            System.out.println(intArray[i] + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        OrderArray orderArray = new OrderArray(5);

        orderArray.insert(8);
        orderArray.insert(3);
        orderArray.insert(5);
        orderArray.insert(2);
        orderArray.delete(3);
        orderArray.insert(9);
        orderArray.insert(7);

        int i = orderArray.find(8);
        System.out.println("在队列中的位置是:" + i);
        System.out.println(Arrays.toString(orderArray.intArray));
    }
}
