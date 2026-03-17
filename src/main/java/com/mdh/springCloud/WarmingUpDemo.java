package com.mdh.springCloud;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

/**
 * Guava 的 RateLimiter
 *
 * 平滑预热限流 (SmoothWarmingUp)
 */
public class WarmingUpDemo {
    public static void main(String[] args) {
        // 创建一个预热限流器
        // 参数：每秒生成2个令牌，预热期3秒
        RateLimiter limiter = RateLimiter.create(2.0, 3, TimeUnit.SECONDS);

        for (int i = 0; i < 10; i++) {
            double waitTime = limiter.acquire();
            System.out.println("请求" + (i+1) + "，等待时间: " + waitTime + "秒");
        }
    }
}
