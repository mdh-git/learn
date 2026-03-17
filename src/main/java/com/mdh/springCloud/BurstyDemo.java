package com.mdh.springCloud;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Guava 的 RateLimiter
 *
 * 平滑突发限流 (SmoothBursty)
 */
public class BurstyDemo {
    public static void main(String[] args) {
        // 创建一个每秒生成2个令牌的限流器
        RateLimiter limiter = RateLimiter.create(2.0);

        // 第一次请求，会立即获取到令牌
        System.out.println("第一次请求: " + limiter.tryAcquire()); // true

        // 连续请求5次，观察等待时间
        for (int i = 0; i < 5; i++) {
            double waitTime = limiter.acquire(); // 阻塞直到获取令牌
            System.out.println("第" + (i+2) + "次请求，等待时间: " + waitTime + "秒");
        }
    }
}
