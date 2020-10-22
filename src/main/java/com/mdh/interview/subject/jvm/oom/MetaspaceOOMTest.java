package com.mdh.interview.subject.jvm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author MDH
 * 2020/10/22 21:35
 *
 * java.lang.OutOfMemoryError: Metaspace
 *
 * JVM参数
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
 *
 * Java8及之后的版本使用Metaspace来代替永久代
 *
 * Metaspace是方法区在HotSpot中的实现,它与持久代最大的区别在于：Metaspace并不在虚拟机内存中而是使用本地内存
 * 也即在Java8中,class metadata(the virtual machines internal presentation of java class),被储存在叫做Metaspace的native memory
 *
 * 永久代(java8后被元空间Metaspace取代了)存放了以下信息：
 *
 * 虚拟机加载的类信息
 * 常量池
 * 静态变量
 * 即时编译后的代码
 *
 * 模拟Metaspace空间溢出,不断生成类往元空间,类占据的空间总是会超出Metaspace指定的空间大小
 */
public class MetaspaceOOMTest {
    static class OOMTest {

    }
    public static void main(String[] args) {
        // 模拟计数器多少次以后发生异常
        int i = 0;
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, args);
                    }
                });
                enhancer.create();
            }
        } catch (Exception e) {
            System.out.println("********多少次后发生了异常:" + i);
            e.printStackTrace();
        }
    }
}
