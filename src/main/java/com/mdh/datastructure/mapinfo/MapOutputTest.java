package com.mdh.datastructure.mapinfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map的三种输出方式
 * 1、toString
 * 2、先把key放在set中，然后根据key找到value输出
 * 3、entrySet 效率最高，建议使用
 * @author madonghao
 * @date 2018/11/6
 */
public class MapOutputTest {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put(1,"chen");
        map.put(2,"chen");
        map.put(3,"zhang");
        map.put(4,"wang");
        map.put(2,"sun");

        // 1、调用默认的toString
        System.out.println(map);
        System.out.println("\n----------------------");

        // 2、
        Set keys = map.keySet();
        for(Object key : keys) {
            System.out.println(key + "=" + map.get(key));
        }
        System.out.println("\n----------------------");

        // 3、
        Set<Map.Entry> ms = map.entrySet();
        for(Map.Entry entry : ms) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("\n----------------------");
    }
}
