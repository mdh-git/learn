package com.mdh.streams;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Lists.transform：能够轻松的从一种类型的list转换为另一种类型的list。
 * @author madonghao
 * @create 2020-03-17 15:16
 **/

public class Demo {

    /**
     * 使用场景：
     * 把一个List<Map<String,String>> 中的  所有map对应某个key的value值取出来 返回一个新的list
     */
    @Test
    public void test01(){

        Map<String, String> map = new HashMap<>();
        map.put("1","test1");
        map.put("1","test2");
        map.put("2","test2");
        Map<String,String> map2 = Maps.newHashMap();
        map2.put("1","test3");
        map2.put("4","test2");
        List<Map<String,String>> list2= Lists.newArrayList();
        list2.add(map);
        list2.add(map2);
        List<String> list3=Lists.transform(list2,s->s.get("1"));
        list3.forEach(s -> System.out.println(s));
    }

    /**
     * 当Lists.transform（）处理的list的值发生改变，那么Lists.transform（）处理的结果也会发生改变
     */
    @Test
    public void test02(){

        List<Fruit> resultList=Lists.newArrayList(new Fruit(1,"苹果"),new Fruit(2,"橙子"),new Fruit(3,"芒果"));
        //将一个list转化为另外一个list
        List<String> nameList=Lists.transform(resultList,v -> v.getName());

        nameList.forEach(s -> System.out.println(s));
        System.out.println("============");
        resultList.forEach(result -> {
            result.setName("梨");
        });
        nameList.forEach(s -> System.out.println(s));
    }


    @Test
    public void test03(){
        Food food = new Food(
                Lists.newArrayList(new Fruit(1,"苹果"),new Fruit(2,"橙子"),new Fruit(3,"芒果")),
                Lists.newArrayList(new Meat(1,"牛肉"),new Meat(2,"猪肉"),new Meat(3,"羊肉"))
        );

        List<Map> collect = food.getFruitList().stream().map(
                fruit -> food.getMeatList().stream()
                        .filter(meat -> Objects.equals(fruit.getId(), meat.getId()))
                        .findAny().map(meat -> {
                            Map map = new HashMap<Integer, String>();
                            map.put(meat.getId(), meat.getName() + "," + fruit.getName());
                            return map;
                        }).orElse(null)
        ).collect(Collectors.toList());

        System.out.println(collect);

    }

    @Test
    public void test04(){
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("5");
        list1.add("6");

        List<String> list2 = new ArrayList<String>();
        list2.add("2");
        list2.add("3");
        list2.add("7");
        list2.add("8");

        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
        System.out.println("---交集 intersection---");
        intersection.parallelStream().forEach(System.out :: println);

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
        System.out.println("---差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out :: println);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(Collectors.toList());
        System.out.println("---差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out :: println);

        // 并集
        List<String> listAll = list1.parallelStream().collect(Collectors.toList());
        List<String> listAll2 = list2.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        System.out.println("---并集 listAll---");
        listAll.parallelStream().forEachOrdered(System.out :: println);

        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(Collectors.toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEachOrdered(System.out :: println);

        System.out.println("---原来的List1---");
        list1.parallelStream().forEachOrdered(System.out :: println);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEachOrdered(System.out :: println);
    }

    @Test
    public void test05(){

        Map<Integer, String> map = new HashMap<>(3);
        System.out.println(map.size());
        System.out.println(map);
    }
}