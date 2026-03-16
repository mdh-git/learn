package com.mdh.algorithm.bloomFilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;

public class GuavaBloomFilter {
    public static void main(String[] args) {
        // 预期插入元素数量
        long expectedInsertions = 1000000L;
        // 可接受的误判率 (0.01 代表 1%)
        double fpp = 0.01;

        // 创建布隆过滤器
        // Funnels.stringFunnel 指定编码为 UTF-8
        BloomFilter<String> bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                expectedInsertions,
                fpp
        );

        // 添加数据
        bloomFilter.put("user_1001");
        bloomFilter.put("user_1002");
        bloomFilter.put("user_1003");

        // 判断是否存在
        System.out.println(bloomFilter.mightContain("user_1001")); // true (可能存在)
        System.out.println(bloomFilter.mightContain("user_9999")); // false (一定不存在)

        // 统计信息
        System.out.println("预计插入数量: " + expectedInsertions);
        System.out.println("实际插入数量: " + bloomFilter.approximateElementCount());
    }
}
