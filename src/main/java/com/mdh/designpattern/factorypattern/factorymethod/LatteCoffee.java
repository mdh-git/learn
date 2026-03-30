package com.mdh.designpattern.factorypattern.factorymethod;

public class LatteCoffee extends Coffee{

    @Override
    public String getName() {
        return "拿铁咖啡";
    }

    @Override
    public void addMilk() {
        System.out.println("拿铁咖啡..添加牛奶");
    }

    @Override
    public void addSugar() {
        System.out.println("拿铁咖啡..添加糖");
    }
}
