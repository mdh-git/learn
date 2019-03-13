package com.mdh.jvm.head;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区溢出的情况，有dump文件
 * java.lang.OutOfMemoryError: PermGen space
 * 设置VM参数  -XX:+HeapDumpOnOutOfMemoryError -XX:PermSize=10m -XX:MaxPermSize=10m
 * @Author: madonghao
 * @Date: 2019/3/13 15:29
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        for (;;) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, args);
                }
            });
            enhancer.create();
        }
    }
    static class OOMObject {

    }
}
