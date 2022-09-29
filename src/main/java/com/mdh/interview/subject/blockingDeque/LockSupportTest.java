package com.mdh.interview.subject.blockingDeque;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: madonghao @Date: 2021/12/29 10:37 上午
 *
 * <p>JDK提供的park/unpark用于阻塞、唤醒线程，它是基于permit（许可证）机制，对执行顺序没要求。
 */
public class LockSupportTest {

  private static int count = 1;

  static class Threads {
    Thread t1;
    Thread t2;

    void join() throws InterruptedException {
      t1.join();
      t2.join();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    final Threads threads = new Threads();

    threads.t1 =
        new Thread(
            () -> {
              System.out.println("t1: " + count++);

              // 唤醒t2
              LockSupport.unpark(threads.t2);
              // 阻塞自己
              LockSupport.park();
            });

    threads.t2 =
        new Thread(
            () -> {
              System.out.println("t1: " + count++);

              // 唤醒t1
              LockSupport.unpark(threads.t1);
              // 阻塞自己
              if (count <= 100) {
                LockSupport.park();
              }
            });

    threads.t1.start();
    Thread.sleep(1);
    threads.t2.start();

    threads.join();
  }
}
