package com.mdh.spring.taskExecutor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: madonghao
 * @Date: 2018/12/28 17:06
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
        AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
        for(int i = 0; i < 10; i++){
            asyncTaskService.executeAsyncTask(i);
            asyncTaskService.executeAsyncTaskPlus(i);
        }
        context.close();
    }
}
