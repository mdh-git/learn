package com.mdh.future;

/**
 *
 * @author madonghao
 * @date 2018/10/22
 */
public class ThreadTestDemo {

    public static void main(String[] args) {

        for(int i = 0;i < 10; i++){
            new Thread(){
                @Override
                public void run() {
                    int j = 0;
                    System.out.println(j++);
                }
            }.start();
        }
    }
}
