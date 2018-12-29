package com.mdh.spring.taskExecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: madonghao
 * @Date: 2018/12/27 20:11
 */
@Service
public class AsyncTaskService {

    @Async//通过@Async注解表明方法是异步方法，如果注解在类级别，则表明改类所有的方法都是异步方法，而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor。
    public void executeAsyncTask(Integer i){
        System.out.println("执行异步任务:" + i);
    }

    @Async
    public void executeAsyncTaskPlus(Integer i){
        System.out.println("执行异步任务+1:" + (i + 1));
    }
}
