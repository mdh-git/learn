package com.mdh.interview.subject.blockingDeque;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author MDH
 * 2020/9/15 23:34
 *
 * 阻塞队列
 *
 */
public class BlockingDequeDemo {
    public static void main(String[] args) {
        //ArrayBlockingQueue<>
//        LinkedBlockingQueue
//        SynchronousQueue
        BlockingQueue blockingQueue = new ArrayBlockingQueue(10);
    }
}
