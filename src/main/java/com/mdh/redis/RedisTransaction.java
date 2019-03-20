package com.mdh.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @Author : mdh
 * @Date: 2019/3/21
 */
public class RedisTransaction {

    public static void main(String[] args) {
        RedisTransaction redisTransaction = new RedisTransaction();
        boolean method = redisTransaction.transMethod();
    }

    private boolean transMethod() {

        Jedis jedis = new Jedis("localhost", 6379);
        //可用余额
        int balance;
        //欠额
        int dept;
        //消费
        int amtToSubtract = 10;

        jedis.watch("balance");
        balance = Integer.valueOf(jedis.get("balance"));
        if(balance < amtToSubtract){
            jedis.unwatch();
            System.out.println("失败");
            return false;
        }else {
            Transaction multi = jedis.multi();
            multi.incrBy("dept", amtToSubtract);
            multi.decrBy("balance",amtToSubtract);
            multi.exec();
            return true;
        }
    }
}
