package com.mdh.designpattern.handler;

public class Test {
    public static void main(String[] args) {

        DateHandler handler = new DateHandler();
        NumberHandler handler1 = new NumberHandler();
        StringHandler handler2 = new StringHandler();
        handler.setNextHandler(handler1);
        handler1.setNextHandler(handler2);

        handler.transformStr("adbc");
        System.out.println("责任链");
    }
}
