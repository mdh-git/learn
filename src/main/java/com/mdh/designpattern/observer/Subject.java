package com.mdh.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者
 *
 * @author madonghao
 * @create 2020-07-06 14:35
 **/
abstract public class Subject {

    private List<Observer> observerList = new ArrayList<>();

    public void attachObserver(Observer observer){
        observerList.add(observer);
    }

    public void detachObserver(Observer observer){
        observerList.remove(observer);
    }

    public void notifyObservers(){
        for (Observer observer: observerList){
            observer.update();
        }
    }
}
