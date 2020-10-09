package com.mdh.designpattern.proxypattern;

/**
 * 静态代理
 *
 * @Author : mdh
 * @Date: 2019/9/21
 */
interface ClothFactory{
    void produceCloth();
}

/**
 * 代理类
 */
class ProxyClothFactory implements ClothFactory{

    /** 用被代理类对象实例化 */
    private ClothFactory  factory;

    public ProxyClothFactory(ClothFactory  clothFactory){
        this.factory = clothFactory;
    }

    @Override
    public void produceCloth() {
        System.out.println("代理工厂做一些准备工作");

        factory.produceCloth();

        System.out.println("代理工厂做一些后续的收尾工作");
    }
}

/**
 * 被代理类
 */
class NikeClothFactory implements ClothFactory{

    @Override
    public void produceCloth() {
        System.out.println("被代理类执行的工作");
    }
}


public class StaticProxyTest {

    public static void main(String[] args) {
        // 创建被代理类的对象
        ClothFactory nick = new NikeClothFactory();
        // 创建代理类的对象
        ClothFactory proxyClothFactory = new ProxyClothFactory(nick);
        proxyClothFactory.produceCloth();
    }
}
