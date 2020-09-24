package com.mdh.interview.subject.callable;

/**
 * @author madonghao
 * @create 2020-09-24 15:13
 **/
public class ThreadTask implements Runnable {

    private String taskName;

    @Override
    public void run() {
        //输出执行线程的名称
        System.out.println("TaskName"+this.getTaskName()+"---ThreadName:"+Thread.currentThread().getName());
    }

    public ThreadTask(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
