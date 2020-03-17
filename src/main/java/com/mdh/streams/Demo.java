package com.mdh.streams;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
}
