package com.mdh.designpattern.factorypattern.abstractfactory;

public class MacTextField implements TextField {
    @Override
    public void input(String text) {
        System.out.println("macOS 文本框输入: " + text);
    }
}
