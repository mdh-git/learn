package com.mdh.designpattern.proxypattern.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * 获取代理对象的工厂类
 * @author madonghao
 * @create 2020-10-09 9:54
 **/
public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new DebugInvocationHandler(target)
        );
    }
}
