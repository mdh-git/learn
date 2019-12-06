package com.mdh.demo;


import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

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
        User user1 = new User();
        user1.setAge(12);
        user1.setName("name1");
        user1.setTitle("title1");
        User user2 = new User();
        user2.setAge(12);
        user2.setName("name2");
        user2.setSex(1);
        user2.setTitle(null);
        List<User> users = Arrays.asList(user1, user2);
        List<User> collect = users.stream().filter(
                v -> !StringUtils.isEmpty(v.getTitle())
        ).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(collect));

    }

}
