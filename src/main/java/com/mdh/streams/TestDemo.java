package com.mdh.streams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * stream练习题
 * @author madonghao
 * @create 2020-09-02 10:46
 **/
public class TestDemo {

    private List<Transaction> build() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> list = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        return list;
    }


    /**
     * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
     */
    @Test
    public void test01(){
        List<Transaction> list = build();
        List<Transaction> collect = list.stream()
                .filter(v -> v.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     *  (2) 交易员都在哪些不同的城市工作过？
     */
    @Test
    public void test02(){
        List<Transaction> list = build();
        List<String> collect = list.stream()
                .map(v -> v.getTrader().getCity())
                .distinct().collect(Collectors.toList());
        System.out.println(collect);

        List<String> collect1 = list.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect1);
    }

    /**
     * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
     */
    @Test
    public void test03(){
        List<Transaction> list = build();
        List<Trader> collect = list.stream()
                .filter(v -> v.getTrader().getCity().equals("Cambridge"))
                .map(v -> v.getTrader())
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(collect);

        List<Trader> cambridge = list.stream()
                .map(Transaction::getTrader)
                .filter(t -> StringUtils.pathEquals("Cambridge", t.getCity()))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(cambridge);
    }

    /**
     * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
     */
    @Test
    public void test04(){
        List<Transaction> list = build();
        List<String> collect = list.stream()
                .map(v -> v.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(collect);

        String collect1 = list.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .sorted()
                .collect(Collectors.joining());
        System.out.println(collect1);
    }

    /**
     * (5) 有没有交易员是在米兰工作的？
     */
    @Test
    public void test05(){
        List<Transaction> list = build();
        boolean milan = list.stream()
                .anyMatch(v -> v.getTrader().getCity().equals("Milan"));
        System.out.println(milan);

        boolean mario = list.stream()
                .map(Transaction::getTrader)
                .anyMatch(t -> StringUtils.pathEquals(t.getCity(), "mario"));
        System.out.println(mario);
    }

    /**
     *  (6) 打印生活在剑桥的交易员的所有交易额。
     */
    @Test
    public void test06(){
        List<Transaction> list = build();
        list.stream()
                .filter(v -> v.getTrader().getCity().equals("Cambridge"))
                .forEach(System.out::println);

        list.stream()
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    /**
     * (7) 所有交易中，最高的交易额是多少？
     */
    @Test
    public void test07(){
        List<Transaction> list = build();
        Optional<Transaction> max = list.stream()
                .max(Comparator.comparing(Transaction::getValue));
        System.out.println(max.get().getValue());

        Optional<Transaction> max1 = list.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Transaction::getValue)));
        System.out.println(max1.get().getValue());
    }

    /**
     * (8) 找到交易额最小的交易
     */
    @Test
    public void test08(){
        List<Transaction> list = build();
        Optional<Transaction> min = list.stream()
                .min(Comparator.comparing(Transaction::getValue));
        System.out.println(min.get().getValue());
    }

    /**
     * 流的构建
     */
    @Test
    public void test09(){
        Stream<String> hello = Stream.of("hello", "word");
        hello.forEach(System.out::println);

        Stream<Object> empty = Stream.empty();

        int[] nums = {1, 2, 3, 4};
        IntStream stream = Arrays.stream(nums);
        int sum = stream.sum();
        System.out.println(sum);
    }
}

/**
 * 交易者类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
class Trader{
    private String name;
    private String city;
}

/**
 * 交易类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
class Transaction {
    private Trader trader;
    private int year;
    private int value;
}
