package com.mdh.interview.subject.blockingDeque;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABCTwo {

    private final int max;

    // 用来指示当前应该打印的线程序号，0-A, 1-B, 2-C
    private int turn = 0;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition conditionA = lock.newCondition();

    private final Condition conditionB = lock.newCondition();

    private final Condition conditionC = lock.newCondition();

    public void printA(){
        print("A", conditionA, conditionB);
    }

    public void printB(){
        print("B", conditionB, conditionC);
    }

    public void printC(){
        print("C", conditionC, conditionA);
    }

    public PrintABCTwo(int max) {
        this.max = max;
    }

    public void print(String name, Condition cur, Condition next){
        for (int i = 0; i < max; i++) {
            lock.lock();
            try{
                // 等待直到轮到当前线程打印
                // turn 变量的值需要与线程要打印的字符相对应，例如，如果turn是0，且当前线程应该打印"A"，则条件满足。如果不满足，当前线程调用currentCondition.await()进入等待状态。
                while (!((turn == 0 && name.charAt(0) == 'A') || (turn == 1 && name.charAt(0) == 'B') || (turn == 2 && name.charAt(0) == 'C'))) {
                    cur.await();
                }
                System.out.println(Thread.currentThread().getName() + " : " + name);
                // 更新打印轮次，并唤醒下一个线程
                turn = (turn + 1) % 3;
                next.signal();
            } catch (InterruptedException e){
                Thread.currentThread().isInterrupted();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        PrintABCTwo print = new PrintABCTwo(10);
        Thread t1 = new Thread(print::printA, "A");
        Thread t2 = new Thread(print::printB, "B");
        Thread t3 = new Thread(print::printC, "C");

        t1.start();
        t2.start();
        t3.start();
    }
}
