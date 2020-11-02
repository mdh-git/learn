# Spring
~~~
AOP  IOC  TX(事务)
~~~

## IOC
~~~
IoC（Inverse of Control:控制反转）是一种设计思想，就是 将原本在程序中手动创建对象的控制权，交由Spring框架来管理。

IoC 在其他语言中也有应用，并非 Spring 特有。 
IoC 容器是 Spring 用来实现 IoC 的载体， IoC 容器实际上就是个Map（key，value）,Map 中存放的是各种对象。

将对象之间的相互依赖关系交给Ioc容器来管理,并由Ioc容器来完成对象的注入。这样可以很大程度简化应用的开发,把应用从复杂的依赖关系
中解脱出来。IOC容器就像一个工厂一样,当需要创建一个对象的时候,只需要配置好配置文件/注解即可,完全不用考虑对象是如何被创建出来的。

Spring Ioc的初始化过程:

XML   --读取-->   Resource    --解析-->   BeanDefinition  --注册-->BeanFactory

源码解析：https://javadoop.com/post/spring-ioc
~~~

## AOP
~~~
AOP(Aspect-Oriented Programming:面向切面编程)能够将那些与业务无关,却为业务模块共同调用的逻辑或责任(例如事务处理、日志管理、
权限控制等)封装起来,便于减少系统的重复代码,降低模块间的耦合性,并有利于未来的可扩展性和维护性。

Spring AOP就是基于动态代理的,
（1）如果要代理的对象,实现了某个接口,那么Spring AOP会使用JDK Proxy,去创建代理对象
（2）没有实现接口的对象,就无法使用JDK Proxy去进行代理了,这时候Spring会使用Cglib生成一个被代理对象的子类来作为代理
~~~

## Spring AOP 和 AspectJ AOP 有什么区别？
~~~
Spring AOP属于运行时增强,而AspectJ是编译时增强。
Spring AOP基于代理(Proxying),而AspectJ字节码操作(Bytecode Manipulation)。

Spring AOP 已经集成了 AspectJ ，AspectJ 应该算的上是 Java 生态系统中最完整的 AOP 框架了。
AspectJ 相比于 Spring AOP 功能更加强大，但是 Spring AOP 相对来说更简单。
~~~

## Spring 中的单例 bean 的线程安全问题了解吗？
~~~
大部分时候我们并没有在系统中使用多线程，所以很少有人会关注这个问题。
单例 bean 存在线程问题，主要是因为当多个线程操作同一个对象的时候，对这个对象的非静态成员变量的写操作会存在线程安全问题。

常见的有两种解决办法：
(1):在Bean对象中尽量避免定义可变的成员变量（不太现实）。
(2):在类中定义一个ThreadLocal成员变量，将需要的可变成员变量保存在 ThreadLocal 中（推荐的一种方式）。
~~~

## Spring 中的 bean 生命周期?
~~~
Bean容器找到配置文件中Spring Bean的定义。
Bean容器利用Java Reflection API 创建一个Bean的实例。
如果涉及到一些属性值 利用set()方法设置一些属性值。
如果Bean实现了 BeanNameAware 接口,调用 setBeanName() 方法,传入Bean的名字。
如果Bean实现了 BeanClassLoaderAware 接口,调用 setBeanClassLoader() 方法,传入ClassLoader对象的实例。
与上面的类似,如果实现了其他 *.Aware 接口,就调用对应的方法。
如果有和加载这个Bean的Spring容器相关的 BeanPostProcess对象,执行postProcessBeforeInitialization()方法
如果实现了 InitialzingBean接口,执行afterPropertiesSet()方法。
如果 Bean 在配置文件中的定义包含 init-method 属性，执行指定的方法。
如果有和加载这个 Bean的 Spring 容器相关的 BeanPostProcessor 对象，执行postProcessAfterInitialization() 方法
当要销毁 Bean 的时候，如果 Bean 实现了 DisposableBean 接口，执行 destroy() 方法。
当要销毁 Bean 的时候，如果 Bean 在配置文件中的定义包含 destroy-method 属性，执行指定的方法。

实例化bean对象
设置对象属性
检查Aware相关接口并设置相关的依赖
BeanPostProcessor前置处理
检查是否是InitializingBean以决定是否调用afterPropertiesSet方法
检查是否配置有自定义的init-method
BeanPostProcessor后置处理
注册必要的Destruction相关回调接口
使用中
是否实现DisposbaleBean接口
是否配置有自定义的destory方法
~~~

##  Spring 框架中用到了哪些设计模式？
~~~
https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485303&idx=1&sn=9e4626a1e3f001f9b0d84a6fa0cff04a&chksm=cea248bcf9d5c1aaf48b67cc52bac74eb29d6037848d6cf213b0e5466f2d1fda970db700ba41&token=255050878&lang=zh_CN#rd

工厂设计模式 : Spring使用工厂模式通过 BeanFactory、ApplicationContext 创建 bean 对象。
代理设计模式 : Spring AOP 功能的实现。
单例设计模式 : Spring 中的 Bean 默认都是单例的。
模板方法模式 : Spring 中 jdbcTemplate、hibernateTemplate 等以 Template 结尾的对数据库操作的类，它们就使用到了模板模式。
包装器设计模式 : 我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要会去访问不同的数据库。这种模式让我们可以根据客户的需求能够动态切换不同的数据源。
观察者模式: Spring 事件驱动模型就是观察者模式很经典的一个应用。
适配器模式 :Spring AOP 的增强或通知(Advice)使用到了适配器模式、spring MVC 中也是用到了适配器模式适配Controller。
~~~
