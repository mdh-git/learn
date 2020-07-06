package com.mdh.designpattern.observer;

/**
 * 主角
 *
 * @author madonghao
 * @create 2020-07-06 14:45
 **/
public class Hero extends Subject {
    void move(){
        System.out.println("主角向前移动");
        notifyObservers();
    }
}
