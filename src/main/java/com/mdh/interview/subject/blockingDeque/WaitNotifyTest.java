package com.mdh.interview.subject.blockingDeque;

/**
 * @Author: madonghao @Date: 2021/12/29 10:26 上午
 *
 * 启动两个线程, 一个输出 1,3,5,7…99, 另一个输出 2,4,6,8…100，最后按顺序输出 1,2,3,4,5…100
 *
 * 思路:
 * 线程1输出一个数字后，累加计数器，然后唤醒线程2并等待；线程2开始执行，执行的内容与线程1相同。
 * 这里因为只有2个线程，所以无需判断奇偶，只需要控制执行顺序就行了。
 */
public class WaitNotifyTest {

  private static int count = 1;

  private static Object lock = new Object();

  public static void main(String[] args) throws InterruptedException {

    Thread t1 =
        new Thread(
            () -> {
              while (count <= 100) {
                synchronized (lock) {
                  System.out.println("odd: " + count++);
                  lock.notify();

                  try {
                    lock.wait();
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
              }
            });

    Thread t2 = new Thread(() -> {
        while (count <= 100){
            synchronized (lock) {
                System.out.println("even: " + count++);
                lock.notify();

                // 为了让程序正常退出
                // 在输出100后，结束线程
                if(count == 101){
                    return;
                }

                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    t1.start();

    // 为避免顺序出错，先sleep再启动t2
    Thread.sleep(1);

    t2.start();

    // 等待最后执行的线程结束
    t2.join();
  }
}
