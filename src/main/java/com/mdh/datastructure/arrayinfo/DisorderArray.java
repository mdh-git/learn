package com.mdh.datastructure.arrayinfo;

/**
 * 数组就是一段连续的内存，即使在物理内存中不是连续的，在逻辑上肯定是连续的
 *
 * 无序数组(没有重复值)
 * 优点:插入快，如果知道下标，可以很快的存取
 * 缺点:查找慢，删除慢，大小固定
 *
 * @author madonghao
 * @date 2018/10/25
 */
public class DisorderArray {

    /** 数组 */
    private String[] strArray;
    /** 构造方法，传入数组最大长度 */
    private int length = 0;


    /**
     * 构造方法，传入数组最大长度
     * @param max
     */
    public DisorderArray(int max) {
        strArray = new String[max];
    }

    /**
     * 检测数组是否包含某个元素，如果存在返回其下标，不存在则返回-1
     * @param target
     * @return
     */
    public int contains(String target) {
        int index = -1;
        for(int i = 0 ;i < length; i++) {
            if(strArray[i].equals(target)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 插入
     * @param elem
     */
    public void insert(String elem) {
        strArray[length] = elem;
        length++;
    }

    /**
     * 删除某个指定的元素值，删除成功则返回true，否则返回false
     * @param target
     * @return
     */
    public boolean delete(String target) {
        int index = -1;
        if((index = contains(target)) != -1) {
            for(int i = index; i < length -1; i++) {
                strArray[i] = strArray[i+1];
            }
            length--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 列出所有元素
     */
    public void display() {
        for(int i = 0; i < length; i++) {
            System.out.print(strArray[i] + "\t");
        }
        System.out.println();
    }

    //测试
    public static void main(String[] args) {
        /**
         * 最多插入给定max大小的数据，如果超过max个会抛出下面的异常
         * java.lang.ArrayIndexOutOfBoundsException: 下标
         */
        DisorderArray arr = new DisorderArray(5);
        arr.insert("a");
        arr.insert("i");
        arr.insert("c");
        arr.insert("d");
        arr.insert("e");
        arr.delete("c");
        //j 不存在，删除
        arr.delete("j");
        arr.insert("f");
        arr.display();

        System.out.println("*************************");

        String[] str = new String[7];
        for(int i = 0, len = str.length; i < len; i++) {
            str[i] = "" + i + "";
        }
        for (int i = 0, len = str.length; i < len; i++){
            System.out.print(str[i] + "\t");
        }
    }
}
