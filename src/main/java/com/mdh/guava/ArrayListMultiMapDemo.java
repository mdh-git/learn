package com.mdh.guava;

import com.google.common.collect.ArrayListMultimap;
import org.openjdk.jmh.util.Multimap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListMultiMapDemo {

    public static void main(String[] args) {

        Person p1 = new Person().setId(1).setName("syl").setSex("男");
        Person p2 = new Person().setId(1).setName("syl1").setSex("男");
        Person p3 = new Person().setId(2).setName("syl2").setSex("女");
        Person p4 = new Person().setId(3).setName("syl3").setSex("女");
        Person p5 = new Person().setId(3).setName("syl3").setSex("女");

        //使用map
        toMap(p1, p2, p3, p4, p5);
        //使用multimap
        toMultimap(p1, p2, p3, p4, p5);

        List<Integer> list = Stream.of(1, 2, 3).collect(Collectors.toList());
        list.add(4);
        //list1不能进行删增
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        //list1.add(4);
    }

    /**
     *使用multimap,key重复时,value不会被覆盖
     */
    private static void toMultimap(Person a1, Person a2, Person a3, Person a4,Person a5) {
        List<Person> aList = Stream.of(a1, a2, a3, a4,a5).collect(Collectors.toList());
        //创建multimap
        ArrayListMultimap<Integer, Person> multimap = ArrayListMultimap.create();
        for (Person aa : aList) {
            multimap.put(aa.getId(),aa);
        }
        System.out.println(multimap+" 000");

        for (Map.Entry<Integer, Collection<Person>> aa : multimap.asMap().entrySet()) {
            Integer key = aa.getKey();
            Collection<Person> collection = aa.getValue();
            System.out.println(key+"   ------    "+ collection);
        }


        //对multiMap进行遍历
//        multimap.keys().stream().forEach(k-> System.out.println("key:"+k+"value:"+multimap.get(k)));
        multimap.keys().stream().forEach(k-> System.out.println(k+" ==="));
        //取出multimap的value并且进行遍历
        multimap.values().stream().forEach(a-> System.out.println(a.getName()));
    }

    /**
     *使用map,key重复时,value会被覆盖
     */
    private static void toMap(Person a1, Person a2, Person a3, Person a4, Person a5) {
        //Aa::getId是map中的key,a -> a是map中value,可以通过(k,v)->a2指定重复之后按照那个值输出
        Map<Integer, Person> map = Stream.of(a1, a2,a3,a4).collect(Collectors.toMap(Person::getId, a -> a,(k, v)->a2));
        map.forEach((K,V)->{
            System.out.println(K+" "+V);
        });
    }
}
