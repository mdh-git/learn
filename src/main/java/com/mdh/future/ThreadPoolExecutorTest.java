package com.mdh.future;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *
 * public void execute(Runnable command) {
 *         if (command == null)
 *             throw new NullPointerException();
 *
 * 小于核心线程数
 *         int c = ctl.get();
 *         if (workerCountOf(c) < corePoolSize) {
 *             if (addWorker(command, true))
 *                 return;
 *             c = ctl.get();
 *         }
 * 放入队列
 *         if (isRunning(c) && workQueue.offer(command)) {
 *             int recheck = ctl.get();
 *             if (! isRunning(recheck) && remove(command))
 *                 reject(command);
 *             else if (workerCountOf(recheck) == 0)
 *                 addWorker(null, false);
 *         }
 * 如果直接调用addWorker方法
 *
 *         else if (!addWorker(command, false))
 *             reject(command);
 *     }
 * @author madonghao
 * @date 2018/11/5
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 200, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200));
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5");
        for(String s : list){
            for(int i = 10; i < 10; i++){
                threadPoolExecutor.submit(
                        () -> {
                            System.out.println("hello");
                            return "ok";
                        }
                );
            }
        }

    }
}
