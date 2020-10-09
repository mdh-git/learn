package com.mdh.designpattern.proxypattern.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义 MethodInterceptor（方法拦截器）
 * @author madonghao
 * @create 2020-10-09 11:10
 **/
public class DebugMethodInterceptor implements MethodInterceptor {

    /**
     *
     * @param o             被代理的对象（需要增强的对象）
     * @param method        被拦截的方法（需要增强的方法）
     * @param objects       方法入参
     * @param methodProxy   用于调用原始方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());
        Object object = methodProxy.invokeSuper(o, objects);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return object;
    }
}
