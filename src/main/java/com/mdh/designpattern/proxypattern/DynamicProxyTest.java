package com.mdh.designpattern.proxypattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 *
 * @Author : mdh
 * @Date: 2019/9/21
 */

interface Human{
    String getBelief();
    void eat(String food);
}

/** 被代理类 */
class SuperMan implements Human{
    @Override
    public String getBelief() {
        return "I believe i can fly";
    }

    @Override
    public void eat(String food) {
        System.out.println("我喜欢吃" + food);
    }
}

class HumanUtil{
    public void method1(){
        System.out.println("===========通用方法1===============");
    }

    public void method2(){
        System.out.println("===========通用方法2===============");
    }
}

/**
 * 要实现动态代理,需要解决的问题?
 * 问题一:如何根据加载到内存中的被代理类,动态的创建一个代理类及其对象
 * 问题二:当通过代理类的对象调用方法时,如何动态的去调用被代理类中的同名方法
 */

class ProsyFactory{
    /** 调用此方法,返回一个代理类的对象，解决问题一 */
    public static Object getProxyInstance(Object obj){ // obj: 被代理类的对象
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler();
        myInvocationHandler.bind(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), myInvocationHandler);
    }
}

class MyInvocationHandler implements InvocationHandler {

    /** 需要使用被代理类的对象进行赋值 */
    private Object obj;

    public void bind(Object obj){
        this.obj = obj;
    }
    /** 当通过代理类的对象，调用方法a时，就会自动的调用如下的方法: invoke() */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HumanUtil util = new HumanUtil();
        util.method1();
        /** method:即为代理类对象调用的方法,此方法也做为了被代理类调用的方法
         * obj: 被代理类的对象 */
        Object invoke = method.invoke(obj, args);
        util.method2();
        return invoke;
    }
}
public class DynamicProxyTest {
    public static void main(String[] args) {
        SuperMan man = new SuperMan();
        Human proxyInstance = (Human)ProsyFactory.getProxyInstance(man);
        System.out.println(proxyInstance.getBelief());
        proxyInstance.eat("汉堡");
    }
}
