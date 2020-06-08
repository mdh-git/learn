package com.mdh.streams;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author madonghao
 * @create 2020-06-08 10:12
 **/
public class FlatMapTest {

    @Test
    public void test01(){

        /**获取单词，并且去重**/
        List list = Arrays.asList("beijing changcheng", "beijing gugong", "beijing tiantan","gugong tiananmen");
        list.stream().forEach(System.out:: println);

    }


    @Test
    public void test02(){
        List<StudentScore> studentScores = buildATestList();
        Map<String, Long> collect = studentScores.stream().collect(Collectors.groupingBy(StudentScore::getStuName, Collectors.counting()));
        System.out.println(JSON.toJSONString(collect));

        Map<String, Integer> collect1 = studentScores.stream().collect(Collectors.groupingBy(StudentScore::getStuName, Collectors.summingInt(StudentScore::getScore)));
        System.out.println(JSON.toJSONString(collect1));
    }

    @Test
    public void test03() throws JsonProcessingException {
        List<StudentScore> studentScores = buildATestList();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Integer> studentScoreMap = new HashMap<>();
        studentScores.forEach(studentScore -> {
            if(studentScoreMap.containsKey(studentScore.getStuName())){
                studentScoreMap.put(studentScore.getStuName(),
                        studentScoreMap.get(studentScore.getStuName()) + studentScore.getScore());
            } else {
                studentScoreMap.put(studentScore.getStuName(), studentScore.getScore());
            }
        });

        System.out.println(objectMapper.writeValueAsString(studentScoreMap));
    }

    @Test
    public void test04(){
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        List<StudentScore> studentScores = buildATestList();
        studentScores.forEach(studentScore -> stringIntegerMap.merge(
                studentScore.getStuName(),
                studentScore.getScore(),
                Integer::sum
        ));

        System.out.println(JSON.toJSONString(stringIntegerMap));
    }

    @Test
    public void test05(){
        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal(23.5)),
                new Item("apple", 20, new BigDecimal(32.5)),
                new Item("orange", 30, new BigDecimal(13.9)),
                new Item("orange", 30, BigDecimal.valueOf(13.9)),
                new Item("orange", 20, new BigDecimal(32.5)),
                new Item("orange", 10, new BigDecimal(63.5)),
                new Item("orange", 50, new BigDecimal(41.5)),
                new Item("peach", 20, new BigDecimal(26.5)),
                new Item("peach", 30, new BigDecimal(32.5)),
                new Item("peach", 40, new BigDecimal(24.5)),
                new Item("peach", 10, new BigDecimal(12.5))
        );
        System.out.println(items);

        // 分组
        Map<BigDecimal, List<Item>> groupByPriceMap = items.stream()
                .collect(Collectors.groupingBy(Item::getPrice));
        System.out.println(groupByPriceMap);

        // 分组 转化List->Set
        Map<BigDecimal, Set<String>> result = items.stream()
                .collect(Collectors.groupingBy(Item::getPrice, Collectors.mapping(Item::getName, Collectors.toSet())));
        System.out.println(result);
    }

    private List<StudentScore> buildATestList(){
        List<StudentScore> studentScoreList = new ArrayList<>();
        StudentScore studentScore1 = new StudentScore() {{
            setStuName("张三");
            setSubject("语文");
            setScore(70);
        }};
        StudentScore studentScore2 = new StudentScore() {{
            setStuName("张三");
            setSubject("数学");
            setScore(80);
        }};
        StudentScore studentScore3 = new StudentScore() {{
            setStuName("张三");
            setSubject("英语");
            setScore(65);
        }};
        StudentScore studentScore4 = new StudentScore() {{
            setStuName("李四");
            setSubject("语文");
            setScore(68);
        }};
        StudentScore studentScore5 = new StudentScore() {{
            setStuName("李四");
            setSubject("数学");
            setScore(70);
        }};
        StudentScore studentScore6 = new StudentScore() {{
            setStuName("李四");
            setSubject("英语");
            setScore(90);
        }};
        StudentScore studentScore7 = new StudentScore() {{
            setStuName("王五");
            setSubject("语文");
            setScore(80);
        }};
        StudentScore studentScore8 = new StudentScore() {{
            setStuName("王五");
            setSubject("数学");
            setScore(85);
        }};
        StudentScore studentScore9 = new StudentScore() {{
            setStuName("王五");
            setSubject("英语");
            setScore(70);
        }};

        studentScoreList.add(studentScore1);
        studentScoreList.add(studentScore2);
        studentScoreList.add(studentScore3);
        studentScoreList.add(studentScore4);
        studentScoreList.add(studentScore5);
        studentScoreList.add(studentScore6);
        studentScoreList.add(studentScore7);
        studentScoreList.add(studentScore8);
        studentScoreList.add(studentScore9);

        return studentScoreList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Item {
        private String name;
        private Integer id;
        private BigDecimal price;
    }

    @Test
    public void test06(){
        BigDecimal bigDecimal = new BigDecimal(13.9);
        BigDecimal bigDecimal1 = BigDecimal.valueOf(13.9);
        System.out.println(bigDecimal);
        System.out.println(bigDecimal1);
    }
}
