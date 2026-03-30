package com.mdh.designpattern.factorypattern.abstractfactory;

public class Client {
    public static void main(String[] args) {

        GUIFactory factory;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            factory = new WindowsFactory();
        } else {
            factory = new MacFactory();
        }

        // 客户端通过抽象工厂接口创建产品，无需关心具体类
        Button button = factory.createButton();
        TextField textField = factory.createTextField();

        button.render();
        textField.input("Hello Abstract Factory!");
    }
}
