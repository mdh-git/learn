package com.mdh.designpattern.handler;

/**
 * 应用场景：传入一个字符串，自动识别为日期类型、数字类型还是字符串类型。
 * 抽象处理者角色 - Handler
 */
public abstract class HandlerDemo {

    protected HandlerDemo nextHandler;

    // 传入一个字符串，最后将其转化为需要的类型
    abstract void transformStr(String str);

    // 指定下一个处理者
    public void setNextHandler(HandlerDemo nextHandler) {
        this.nextHandler = nextHandler;
    }
}
