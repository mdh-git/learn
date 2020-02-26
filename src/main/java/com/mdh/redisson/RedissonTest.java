package com.mdh.redisson;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author madonghao
 * @create 2020-01-19 11:28
 **/
public class RedissonTest {



    @Test
    public void test(){

        // 1. Create config object
        Config config = new Config();
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("redis://127.0.0.1:6379");

        // Sync and Async API
        RedissonClient client = Redisson.create(config);
        // RList 继承了 java.util.List 接口
        RList<String> nameList = client.getList("nameList");
        nameList.clear();
        nameList.add("bingo");
        nameList.add("yanglbme");
        nameList.add("yanglbme - Overview");
        nameList.remove(-1);

        boolean contains = nameList.contains("yanglbme");
        System.out.println("List size: " + nameList.size());
        System.out.println("Is list contains name 'yanglbme': " + contains);
        nameList.forEach(System.out::println);
        client.shutdown();
    }
}
