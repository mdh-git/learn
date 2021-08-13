package com.mdh.interview.subject.callable;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: madonghao
 * @Date: 2021/8/11 8:37 下午
 */
public class MyRejectPolicy  implements RejectedExecutionHandler  {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //Sender是我的Runnable类，里面有message字段
        if (r instanceof ThreadTask) {
            ThreadTask task = (ThreadTask) r;
            //直接打印
            System.out.println(task.getName());
        }
    }
}