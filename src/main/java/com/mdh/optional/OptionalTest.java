package com.mdh.optional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * DESCRIPTION: Optional类的操作
 *
 * @author donghao.ma
 * @date 2019/8/6 17:29
 */
public class OptionalTest {
    public static void main(String[] args) {
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
//        Optional<List<Integer>> list1 = Optional.ofNullable(list);
        // 用户ID
        Integer userId = 120;
        // id 转 List
        List<Integer> userIds = Lists.newArrayList(userId);
        System.out.println(JSON.toJSONString(userIds));
        System.out.println(userIds.size());
        System.out.println("DEV_SCENE_EXAMITEM_SIMILARSPEECH".length());

    }
}
