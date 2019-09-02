package com.mdh.collection;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DESCRIPTION: TODO
 *
 * @author donghao.ma
 * @date 2019/8/9 15:33
 */
public class FilterTest {

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        list.add(new Person("abcdef", 1));
        list.add(new Person("abcabcj", 2));
        list.add(new Person("fhfhf", 3));
        List<Person> collect = list.stream().filter(s -> s.getName().contains("abc")).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));
    }

    static class Person{
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }
}
