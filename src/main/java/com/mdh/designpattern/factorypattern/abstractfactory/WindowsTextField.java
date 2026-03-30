package com.mdh.designpattern.factorypattern.abstractfactory;

public class WindowsTextField implements TextField {
    @Override
    public void input(String text) {
        System.out.println("Windows 文本框输入: " + text);
    }
}
