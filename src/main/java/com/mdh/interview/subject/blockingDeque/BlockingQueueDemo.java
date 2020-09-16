package com.mdh.interview.subject.blockingDeque;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author MDH
 * 2020/9/16 21:36
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        //throwException();

        //special();

        block();

        overtime();

    }

    /**
     * 抛出异常
     * 当阻塞队列满时，再往队列里add元素，会抛出java.lang.IllegalStateException: Queue full异常
     * 当阻塞队列空时，再往队列里remove元素，会抛出java.util.NoSuchElementException异常
     */
    private static void throwException() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        System.out.println(blockingQueue.add("a"));// true
        System.out.println(blockingQueue.add("b"));// true
        System.out.println(blockingQueue.add("c"));// true

        //System.out.println(blockingQueue.add("d")); // java.lang.IllegalStateException: Queue full

        System.out.println(blockingQueue.element()); // a

        System.out.println(blockingQueue.remove());// a
        System.out.println(blockingQueue.remove());// b
        System.out.println(blockingQueue.remove());// c

        System.out.println(blockingQueue.remove());// java.util.NoSuchElementException
    }

    /**
     * 特殊值 返回boolean类型
     * 当阻塞队列满时，再往队列里offer元素，返回false
     * 当阻塞队列空时，再往队列里poll元素，返回null
     */
    private static void special() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        System.out.println(blockingQueue.offer("a"));// true
        System.out.println(blockingQueue.offer("b"));// true
        System.out.println(blockingQueue.offer("c"));// true
        System.out.println(blockingQueue.offer("d"));// false

        System.out.println(blockingQueue.peek());// a

        System.out.println(blockingQueue.poll());// a
        System.out.println(blockingQueue.poll());// b
        System.out.println(blockingQueue.poll());// c
        System.out.println(blockingQueue.poll());// null
    }

    /**
     * 一直阻塞
     * 当阻塞队列满时，生产者线程继续往队列里put元素，队列会一直阻塞生产者线程直到put数据或者响应中断退出
     * 当阻塞队列空时，消费者线程试图从队列里take元素，队列会一直阻塞消费者线程直到队列可用
     */
    private static void block() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        try {
            blockingQueue.put("a");
            blockingQueue.put("b");
            blockingQueue.put("c");

            System.out.println(blockingQueue.take());// a
            System.out.println(blockingQueue.take());// b
            System.out.println(blockingQueue.take());// c
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 超时退出
     * 当阻塞队列满时，队列会阻塞生产者线程一段时间，如果超过一定的时间，生产者线程就会退出。
     */
    private static void overtime() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        blockingQueue.offer("a", 2l, TimeUnit.SECONDS);
        blockingQueue.offer("b", 2l, TimeUnit.SECONDS);
        blockingQueue.offer("c", 2l, TimeUnit.SECONDS);

        blockingQueue.poll(2l, TimeUnit.SECONDS);
        blockingQueue.poll(2l, TimeUnit.SECONDS);
        blockingQueue.poll(2l, TimeUnit.SECONDS);
    }

}
