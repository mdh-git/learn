package com.mdh.datastructure.queueinfo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DelayedTask implements Delayed {

    /**
     * 任务到期时间
     */
    private long expirationTime;

    /**
     * 任务
     */
    private Runnable task;

    public void execute() {
        task.run();
    }

    public DelayedTask(long delay, Runnable task) {
        // 到期时间 = 当前时间 + 延迟时间
        this.expirationTime = System.currentTimeMillis() + delay;
        this.task = task;
    }



    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expirationTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(expirationTime, ((DelayedTask)o).expirationTime);
    }


    public static void main(String[] args) throws InterruptedException {
        // 初始化延迟队列
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

        // 添加3个任务，延迟时间分别是3秒、1秒、5秒
        delayQueue.add(new DelayedTask(3000, () -> System.out.println("任务2开始运行")));
        delayQueue.add(new DelayedTask(1000, () -> System.out.println("任务1开始运行")));
        delayQueue.add(new DelayedTask(5000, () -> System.out.println("任务3开始运行")));

        // 运行任务
        System.out.println("开始运行任务");
        while (!delayQueue.isEmpty()) {
            //阻塞获取最先到期的任务
            DelayedTask task = delayQueue.take();
            task.execute();
        }

        List<String> list = Lists.newArrayList();
        list.add("a");
        list.add("ab");
        list.add("abc");
        list.sort(String::compareTo);
        System.out.println(JSON.toJSONString(list));
    }
}
