package com.mdh.redis;

import redis.clients.jedis.Jedis;

/**
 * 单连接redis
 * @author madonghao
 * @date 2018/11/16
 */
public class RedisDemo {

    public static void main(String[] args) {
        //单连接redis
        Jedis jedis = new Jedis("localhost", 6379);
        //通过redis赋值
        jedis.set("s2", "222");
        //通过redis取值
        String result = jedis.get("s2");
        System.out.println(result);

        //关闭jedis
        //jedis.close();
    }

}
