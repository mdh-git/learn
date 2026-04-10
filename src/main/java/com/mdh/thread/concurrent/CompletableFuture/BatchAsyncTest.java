package com.mdh.thread.concurrent.CompletableFuture;

import com.alibaba.fastjson.JSON;
import com.mdh.point.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author mdh-home
 * @date 2026/4/8 - 23:31
 **/
public class BatchAsyncTest {

    public static void main(String[] args) {


        List<Point> list = new ArrayList<>();
        list.add(new Point(1,1));
        list.add(new Point(2,2));
        list.add(new Point(3,3));

        // 2. 定义线程池 (生产环境务必自定义)
        ExecutorService executor = Executors.newFixedThreadPool(10);


        List<CompletableFuture<Point>> futureList = new ArrayList<>();
        for (Point point : list) {
            CompletableFuture<Point> future = CompletableFuture.supplyAsync(() -> buidPoint(point), executor);
            futureList.add(future);
        }

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));

        List<Point> result = allFutures.thenApply(v -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();

        System.out.println(JSON.toJSONString(result));

        executor.shutdown();
    }

    private static Point buidPoint(Point point) {
        return new Point(point.getLongitude()*point.getLongitude(), point.getLatitude()*point.getLatitude());
    }

}
