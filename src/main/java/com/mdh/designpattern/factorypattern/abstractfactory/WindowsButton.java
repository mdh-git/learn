package com.mdh.designpattern.factorypattern.abstractfactory;

public class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("渲染 Windows 风格的按钮");
    }
}
