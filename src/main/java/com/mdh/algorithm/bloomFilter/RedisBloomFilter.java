//package com.mdh.algorithm.bloomFilter;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
///**
// * 基于Redis BitMap手动实现布隆过滤器
// */
//public class RedisBloomFilter {
//
//    // Redis键名
//    private final String key;
//    // bit数组长度
//    private final long bitSize;
//    // 哈希函数个数
//    private final int hashCount;
//
//    private final StringRedisTemplate stringRedisTemplate;
//
//    // 构造器：初始化参数
//    public RedisBloomFilter(String key, long n, double p, StringRedisTemplate stringRedisTemplate) {
//        this.key = key;
//        this.stringRedisTemplate = stringRedisTemplate;
//        // 计算bit数组长度
//        this.bitSize = (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
//        // 计算哈希函数个数
//        this.hashCount = (int) (this.bitSize / n * Math.log(2));
//    }
//
//    // 添加元素到布隆过滤器
//    public void add(Object value) {
//        byte[] bytes = value.toString().getBytes(StandardCharsets.UTF_8);
//        long[] hashes = hash(bytes, hashCount, bitSize);
//        for (long hash : hashes) {
//            // 把对应bit位置置为1
//            stringRedisTemplate.opsForValue().setBit(key, hash, true);
//        }
//    }
//
//    // 判断元素是否存在（存在返回true，不存在返回false；true可能是误判）
//    public boolean contains(Object value) {
//        byte[] bytes = value.toString().getBytes(StandardCharsets.UTF_8);
//        long[] hashes = hash(bytes, hashCount, bitSize);
//        for (long hash : hashes) {
//            // 只要有一个bit位为0，就确定不存在
//            if (!stringRedisTemplate.opsForValue().getBit(key, hash)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    // 多哈希函数实现（基于MD5拆分）
//    private long[] hash(byte[] bytes, int hashCount, long bitSize) {
//        long[] hashes = new long[hashCount];
//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            byte[] digest = md5.digest(bytes);
//            // 把MD5结果（16字节）拆分成多个哈希值
//            for (int i = 0; i < hashCount; i++) {
//                long hash = 0;
//                for (int j = i * 2; j < (i + 1) * 2 && j < digest.length; j++) {
//                    hash = hash * 256 + (digest[j] & 0xFF);
//                }
//                // 确保哈希值在bit数组长度范围内
//                hashes[i] = hash % bitSize;
//            }
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("哈希函数初始化失败", e);
//        }
//        return hashes;
//    }
//}
