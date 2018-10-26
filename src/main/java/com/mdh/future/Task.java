package com.mdh.future;

import javafx.scene.paint.Stop;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 * @author madonghao
 * @date 2018/10/12
 */
public class Task implements Callable<Integer>{

    private String name;
    public Task(String name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(System.currentTimeMillis()+ " " + name + "run");
        Random random = new Random();
        int num = random.nextInt(100);
        Thread.sleep(num);
        System.out.println(System.currentTimeMillis()+ " " + name + "runOver");
        return num;
    }
}
