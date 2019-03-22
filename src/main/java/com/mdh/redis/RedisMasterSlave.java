package com.mdh.redis;

import redis.clients.jedis.Jedis;

/**
 * @Author : mdh
 * @Date: 2019/3/21
 */
public class RedisMasterSlave {
    public static void main(String[] args) {
        //主从复制
        Jedis jedis_M = new Jedis("127.0.0.1", 6379);
        Jedis jedis_S = new Jedis("127.0.0.1", 6380);
        //配置从机
        jedis_S.slaveof("127.0.0.1", 6379);
        //主机写
        jedis_M.set("k1", "v1");
        //从机读
        jedis_S.get("k1");
    }
}
