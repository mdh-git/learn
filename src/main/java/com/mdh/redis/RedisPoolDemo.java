package com.mdh.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 使用连接池连接redis
 * @author madonghao
 * @date 2018/11/16
 */
public class RedisPoolDemo {
    public static void main(String[] args) {
        //JedisPool
        JedisPool jedisPool = new JedisPool("localhost", 6379);
        //通过连接池获取jedis对象
        Jedis jedis = jedisPool.getResource();

        jedis.set("s4", "4444");
        String result = jedis.get("s4");
        System.out.println(result);

        //关闭jedis客户端
        //jedis.close;

        //关闭连接池
        //jedisPool.close();
    }
}
