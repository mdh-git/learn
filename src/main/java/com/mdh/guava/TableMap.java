package com.mdh.guava;

import com.alibaba.fastjson.JSON;
import org.weakref.jmx.internal.guava.collect.*;

import java.util.*;

public class TableMap {


    public static void main(String[] args) {
        Map<String, Map<String, Integer>> map = new HashMap<>();

        Map<String, Integer> workMap = new TreeMap<>();
        workMap.put("Monday", 8);
        workMap.put("Tuesday", 9);
        map.put("Monday", workMap);

        System.out.println(JSON.toJSONString(map));


        // 取出元素
        Integer i = map.get("Monday").get("Tuesday");



        Table<String,String,Integer> table= HashBasedTable.create();
        //存放元素
        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);

        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);

        //取出元素
        Integer dayCount = table.get("Hydra", "Feb");

        //rowKey或columnKey的集合
        Set<String> rowKeys = table.rowKeySet();
        Set<String> columnKeys = table.columnKeySet();

        //value集合
        Collection<Integer> values = table.values();

        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("Hydra","Programmer");
        biMap.put("Tony","IronMan");
        biMap.put("Thanos","Titan");
        //使用key获取value
        System.out.println(biMap.get("Tony"));

        BiMap<String, String> inverse = biMap.inverse();
        //使用value获取key
        System.out.println(inverse.get("Titan"));

        Multimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("day",1);
        multimap.put("day",2);
        multimap.put("day",8);
        multimap.put("month",3);
    }
}
