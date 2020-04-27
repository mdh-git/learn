package com.mdh.demo;


import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author madonghao
 * @date 2018/10/15
 */
public class Test {

    public static void main(String[] args) {
//        User user1 = new User();
//        user1.setAge(12);
//        user1.setName("name1");
//        user1.setTitle("title1");
//        User user2 = new User();
//        user2.setAge(12);
//        user2.setName("name2");
//        user2.setSex(1);
//        user2.setTitle(null);
//        List<User> users = Arrays.asList(user1, user2);
//        List<User> collect = users.stream().filter(
//                v -> !StringUtils.isEmpty(v.getTitle())
//        ).collect(Collectors.toList());
//
//        System.out.println(JSON.toJSONString(collect));

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.removeIf(i -> i.intValue() == 3);
        list.removeIf(i -> i.intValue() == 2);

        List<Integer> list1 = Arrays.asList(1,2,3,4,5,6);
//        list1.removeIf(i -> i.intValue() == 3);
//        list1.removeIf(i -> i.intValue() == 2);

        System.out.println(list);
        System.out.println(list1);

        List<Integer> list2 = new ArrayList<>();
        list2.removeIf(i -> i.intValue() == 3);
        list2.removeIf(i -> i.intValue() == 2);
        System.out.println(list2);

    }

}
