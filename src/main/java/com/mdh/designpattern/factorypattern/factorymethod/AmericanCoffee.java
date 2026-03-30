package com.mdh.designpattern.factorypattern.factorymethod;

public class AmericanCoffee extends Coffee{
    @Override
    public String getName() {
        return "美式咖啡";
    }

    @Override
    public void addMilk() {
        System.out.println("美式咖啡..添加牛奶");
    }

    @Override
    public void addSugar() {
        System.out.println("美式咖啡..添加牛奶");
    }
}
