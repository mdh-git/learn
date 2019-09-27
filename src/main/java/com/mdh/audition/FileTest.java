package com.mdh.audition;

import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
        // 获取父路径
        System.out.println(file.getParentFile());
        // 测试此抽象路径名表示的文件或目录是否存在
        System.out.println(file.exists());
        // 测试此抽象路径名表示的文件是否是一个目录
        System.out.println(file.isDirectory());
        // 测试此抽象路径名表示的文件是否是一个标准文件
        System.out.println(file.isFile());
        // 返回此抽象路径名表示的文件最后一次被修改的时间
        System.out.println(file.lastModified());
        // 返回由此抽象路径名表示的文件的长度
        System.out.println(file.length());
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


    @Test
    public void map(){
        List<Map<String,Object>> resultList= Collections.emptyList();
        resultList.add(new HashMap<>());
    }

    /**
     * 问题：
     * 小明在一家服装店工作。他有一大堆袜子，但他必须按颜色配对出售。
     * 给定一组表示每个袜子颜色的整数，确定有多少对具有匹配颜色的袜子。
     * 例如，有7只带有颜色的袜子，分别是 [1,2,1,2,1,3,2]。有一双带有颜色1的袜子，和一双带有颜色2的袜子，最后剩下三个落单的袜子，所以最后有2双成对的袜子。
     * 输入格式：
     * 第一行包含一个整数，表示袜子只数。第二行包含空格分隔的整数，用于描述每只袜子的颜色。
     * 输出格式：
     * 返回可以出售的成对袜子的总数。
     */
    @Test
    public void mapTest(){
        HashMap<String, Integer> map = new HashMap();
        List<String> list = Arrays.asList("1", "2", "1", "2", "1", "2", "3");
        list.stream().forEach(
                v ->{
                    Object o = map.get(v);
                    if(map.get(v) == null){
                        map.put(v, 1);
                    } else {
                        map.put(v, Integer.valueOf(o.toString()) + 1);
                    }
                }
        );

       Iterator iter = map.entrySet().iterator();
       while (iter.hasNext()){
           Map.Entry next = (Map.Entry) iter.next();
           String key = (String)next.getKey();
           Integer val = (Integer) next.getValue();
           System.out.println(key + ":" + val/2);
       }
    }
}
