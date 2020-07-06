package com.mdh.designpattern.observer;

/**
 * 陷阱
 *
 * @author madonghao
 * @create 2020-07-06 14:43
 **/
public class Trap implements Observer {

    @Override
    public void update() {
        if(inRange()){
            System.out.println("陷阱 困住主角！");
        }
    }

    private boolean inRange(){
        //判断主角是否在自己的影响范围内，这里忽略细节，直接返回true
        return true;
    }
}
