package com.mdh.optional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://blog.csdn.net/zhuwukai/article/details/82888316
 * 1、共同点
 *  都是依赖FuncX(入参，返回值)进行转换（将一个类型依据程序逻辑转换成另一种类型，根据入参和返回值）
 *  都能在转换后直接被subscribe
 * 2、区别
 * map返回的是结果集，flatmap返回的是包含结果集的Observable（返回结果不同）
 * map被订阅时每传递一个事件执行一次onNext方法，flatmap多用于多对多，一对多，再被转化为多个时，一般利用from/just进行一一分发，被订阅时将所有数据传递完毕汇总到一个Observable然后一一执行onNext方法（执行顺序不同）>>>>(如单纯用于一对一转换则和map相同)
 * map只能单一转换，单一只的是只能一对一进行转换，指一个对象可以转化为另一个对象但是不能转换成对象数组（map返回结果集不能直接使用from/just再次进行事件分发，一旦转换成对象数组的话，再处理集合/数组的结果时需要利用for一一遍历取出，而使用RxJava就是为了剔除这样的嵌套结构，使得整体的逻辑性更强。）
 * flatmap既可以单一转换也可以一对多/多对多转换，flatmap要求返回Observable，因此可以再内部进行from/just的再次事件分发，一一取出单一对象（转换对象的能力不同）
 *
 * @author donghao.ma
 * @date 2019/9/7 16:32
 */
public class FlatmapTest {

    public static void main(String[] args) {

        String[] strings = {"Hello", "World"};
        List<Stream<String>> collect = Arrays.asList(strings).stream().
                map(str -> str.split("")).
                map(str -> Arrays.stream(str)).
                collect(Collectors.toList());

    }
}