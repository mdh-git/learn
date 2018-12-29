package com.mdh.binarySystem;

/**
 *
 * 十进制数转换为二进制
 *
 * 1.第一种：除基倒取余法
 * 2.第二种：利用“移位”操作实现
 * 3.调用API函数
 * @Author: madonghao
 * @Date: 2018/12/27 10:41
 */
public class Binary {

    public static void main(String[] args) {

        int num = 5;
        int numStr = 854642432;
        System.out.println("第一种:" + num + "的二进制为  " +binaryToDecimal(num));
        System.out.println("第一种(int大于32位):" + numStr + "的二进制为  " +binaryToDecimal1(numStr));
        System.out.print("第二种(1):" + numStr + "的二进制为:  ");
        binaryToDecimal2(numStr);
        System.out.println();
        System.out.print("第二种(2):" + numStr + "的二进制为:  ");
        binaryToDecimal3(numStr);
        System.out.println();
        System.out.print("第三种:" + numStr + "的二进制为:  " );
        function(numStr);
        System.out.println("----------------------------------------");
        System.out.println("8的二进制里面含有"+ countBit(8) + "个1");
        System.out.println("9的二进制里面含有"+ countBit(9) + "个1");
        System.out.println("----------------------------------------");
        System.out.println("8的二进制里,第4位是不是1?"+ getBit(8,4));
        System.out.println("8的二进制里,第3位是不是1?"+ getBit(8,3));
        System.out.println("----------------------------------------");
        System.out.println("8的二进制里,第3位设置为1"+ setBit1(8,3));
        System.out.println("7的二进制里,第3位设置为0"+ setBit0(7,3));
    }

    /**
     * 第一种：除基倒取余法
     * 即输入一个十进制数n，每次用n除以2，把余数记下来，
     * 再用商去除以2...依次循环，直到商为0结束，
     * 把余数倒着依次排列，就构成了转换后的二进制数。
     *
     * 可以用int的一个数来存储最后的二进制，
     * 每次求余后把余数存储在int型数的低位，依次递增。
     * @param n
     */
    public static int binaryToDecimal(int n){
        //用来记录位数
        int t = 0;
        //用来记录最后的二进制数
        int bin = 0;
        //用来存储余数
        int r = 0;

        while (n != 0){
            r = n % 2;
            n = n / 2;
            bin += r * Math.pow(10,t);
            t++;
        }
        return bin;
    }

    /**
     * int型最大只能表示2^31-1 的正数,存储的二进制数位数有限,
     * 可以使用字符串的拼接（+）来实现
     * @param n
     * @return
     */
    public static String binaryToDecimal1(int n){
        String str = "";
        while (n != 0){
            str = n%2 + str;
            n = n/2;
        }
        return str;
    }

    /**
     * 第二种：利用“移位”操作实现
     * 即：将最高位的数移至最低位（移31位），除过最低位其余位置清零，使用& 操作，
     * 可以使用和1相与（&），由于1在内存中除过最低位是1，其余31位都是零，
     * 然后把这个数按十进制输出；再移次高位，做相同的操作，直到最后一位.
     *
     * 计算机中存储的都是数的补码，
     * 数的原码、反码、补码都是相同的；
     * 负数的原码、反码、补码是不一样的，补码=原码取反+1（符号位不变）
     * >>>为逻辑移位符，向右移n位，高位补0
     * >> 算数移位符，也是向右移n位，不同的是：正数高位补0，负数高位补1
     * << 移位符，向左移n位，低位补0
     * @param n
     */
    public static void binaryToDecimal2(int n){
        for(int i = 31; i >= 0; i--){
            System.out.print(n >>> i & 1);
        }
    }

    public static void binaryToDecimal3(int n){
        for(int i = 0; i < 32; i++){
            int t = (n &0x80000000 >>> i)>>>(31 - i);
            System.out.print(t);
        }
    }

    /**
     * 第三种：调用API函数
     * @param n
     */
    public static void function(int n){
        String result = Integer.toBinaryString(n);
        System.out.println(result);
    }

    /**
     * 计算某个正数的二进制表示法中 1 的个数
     * 算法思路：每次for循环，都将num的二进制中最右边的 1 清除。
     * 为什么n &= (n – 1)能清除最右边的1呢？
     * 因为从二进制的角度讲，n相当于在n - 1的最低位加上1。
     * 举个例子，8（1000）= 7（0111）+ 1（0001），所以8 & 7 = （1000）&（0111）= 0（0000），清除了8最右边的1（其实就是最高位的1，因为8的二进制中只有一个1）。
     * 再比如7（0111）= 6（0110）+ 1（0001），所以7 & 6 = （0111）&（0110）= 6（0110），清除了7的二进制表示中最右边的1（也就是最低位的1）。
     * @param num
     * @return
     */
    private static int countBit(int num){
        int count = 0;
        for(;num > 0;count++){
            num &= (num - 1);
        }
        return count;
    }

    /**
     * 获取某个数的第 i 位（判断某个数的第 i 位是0 还是 1？）
     * 思路：如果第 i 位 与 1 相与 结果为1 表明第 i 位为1；如果为0 表明第 i 位为0
     * 1 左移 i 位后，得到一个数，这个数只有第 i 位为1，其它位都为0
     * num 与这个数相与，得到的结果 要么是0，要么非0。结果为 非0 表示第 i 位为1，结果为0 表示第 i 位为0
     * @param num
     * @param i
     * @return
     */
    private static boolean getBit(int num, int i){
        //true 表示第i位为1,否则为0
        return (num & (1 << i)) != 0;
    }

    /**
     * 将第 i 位设置为1
     * 思路：第 i 位与0 或，值不变。
     *       第 i 位与1 或，变成1。
     * 因此，我们的目标是 num 与 一个第 i 位值为1，其它位的值都为0的数相 或操作
     * @param num
     * @param i
     * @return
     */
    private static int setBit1(int num, int i){
        return (num | (1 << i));
    }

    /**
     * 将第 i 位设置为0（清0）
     * 思路：第 i 位和0与，第 i 位就变成了0。其它位 都与 1 与，其它位保持不变。这样，就只把第 i 位清0了
     * @param num
     * @param i
     * @return
     */
    private static int setBit0(int num, int i){
        //000100
        int mask = ~(1 << i);
        //111011
        return (num & (mask));
    }
}
