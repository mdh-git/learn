package com.mdh.future;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author madonghao
 * @date 2018/10/12
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        userCompletableFuture();
    }

    public static void userCompletableFuture() throws ExecutionException, InterruptedException {
        System.out.println("CompletableFuture");
        CompletableFuture<Void> futureA = CompletableFuture.runAsync(() -> Task("A2"));
        CompletableFuture<Void> futureB = CompletableFuture.runAsync(() -> Task("B2"));
        futureA.runAfterEither(futureB, ()->Task("C2")).get();
    }

    private static Object Task(String name) {
        System.out.println(name + " start at " + LocalTime.now());
        try {
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " ends at " + LocalTime.now());
        return null;
    }
}
