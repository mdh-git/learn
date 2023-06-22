package com.mdh.threadlocal;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 线程通信： 管道通信
 * @author MDH
 * @date 2023/6/22 14:38
 */
public class PipedStreamDemo {

    public static void main(String[] args) throws IOException {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream();
        pipedInputStream.connect(pipedOutputStream);
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    pipedOutputStream.write(i);
                    System.out.println(Thread.currentThread().getName() + "_Producer:" + i);
                    Thread.sleep(2000);
                }
                pipedOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"ProducerThread");

        Thread consumeThread = new Thread(() -> {
            try {
                while (true){
                    int read = pipedInputStream.read();
                    if(read != -1){
                        System.out.println(Thread.currentThread().getName() + "_Consume:" + read);
                    } else {
                        break;
                    }
                    Thread.sleep(1000);
                }
                pipedInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"ConsumeThread");

        producerThread.start();
        consumeThread.start();

    }
}
