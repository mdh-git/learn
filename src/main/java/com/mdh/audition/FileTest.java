package com.mdh.audition;

import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * DESCRIPTION: 2019-09-24号电话面试的题目
 *
 * @author donghao.ma
 * @date 2019/9/25 9:12
 */
public class FileTest {

    /**
     * File 相关操作
     */
    @Test
    public void file(){
        File file = new File("D:\\logs\\service_log.txt");
        // 当前文件的路径
        System.out.println(file.getPath());
        // 获取父路径
        System.out.println(file.getParent());
    }

    /**
     * BigDecimal 的方法
     * scale()  小数的位数
     * add((BigDecimal augend) 数学中的加法
     *
     *
     * stripTrailingZeros() 可以将一个BigDecimal格式化为一个相等的，但去掉了末尾0的BigDecimal
     */
    @Test
    public void bigDecimal(){
        BigDecimal decimal = new BigDecimal("3");
        // add 方法
        System.out.println(decimal.add(new BigDecimal("1")));
        // abs 绝对值
        System.out.println(decimal.abs());
        // multiply 平方
        System.out.println(decimal.multiply(decimal));
        // divide 除法
        System.out.println(decimal.divide(new BigDecimal("2")));
        // 保留小数位
        System.out.println(new BigDecimal("10").divide(new BigDecimal("3"), 10, RoundingMode.HALF_UP));


        BigDecimal d1 = new BigDecimal("123.456789");
        // 四舍五入，123.4568
        BigDecimal d2 = d1.setScale(4, RoundingMode.HALF_UP);
        // 直接截断，123.4567
        BigDecimal d3 = d1.setScale(4, RoundingMode.DOWN);
        System.out.println(d2);
        System.out.println(d3);

    }

    @Test
    public void bigDecimal1(){
        BigDecimal d1 = new BigDecimal("123.45");
        BigDecimal d2 = new BigDecimal("123.4500");
        BigDecimal d3 = new BigDecimal("1234500");
        System.out.println(d1.scale());
        System.out.println(d2.scale());
        System.out.println(d3.scale());
    }

    @Test
    public void bigDecimal2(){
        BigDecimal d1 = new BigDecimal("123.4500");
        BigDecimal d2 = d1.stripTrailingZeros();
        // 4
        System.out.println(d1.scale());
        // 2,因为去掉了00
        System.out.println(d2.scale());

        BigDecimal d3 = new BigDecimal("1234500");
        BigDecimal d4 = d1.stripTrailingZeros();
        // 0
        System.out.println(d3.scale());
        // -2
        System.out.println(d4.scale());
        // BigDecimal的scale()返回负数，例如，-2，表示这个数是个整数，并且末尾有2个0。
    }


}
