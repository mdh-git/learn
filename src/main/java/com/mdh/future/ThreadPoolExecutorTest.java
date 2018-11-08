package com.mdh.future;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
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
