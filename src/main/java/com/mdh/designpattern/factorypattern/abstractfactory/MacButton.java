package com.mdh.designpattern.factorypattern.abstractfactory;

public class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("渲染 macOS 风格的按钮");
    }
}
