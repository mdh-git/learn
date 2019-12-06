package com.mdh.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * zk的测试
 *
 * @author madonghao
 * @create 2019-12-05 19:59
 **/
public class ZkTest {

    public static void main(String[] args) throws Exception {
        /**
         * 超时时间
         */
        final int SESSION_TIME_OUT = 2000;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zookeeper = new ZooKeeper("47.100.77.124:2181", SESSION_TIME_OUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Watch received event");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        System.out.println("zookeeper connection success");

        List<String> children = zookeeper.getChildren("/", false);
        System.out.println(children);
    }
}
