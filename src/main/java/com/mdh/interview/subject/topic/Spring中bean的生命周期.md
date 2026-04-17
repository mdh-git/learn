# Spring中bean的生命周期
~~~
https://blog.csdn.net/qq_40917230/article/details/80257892
https://blog.csdn.net/knknknkn8023/article/details/107130806


Spring Bean 的生命周期是指 Spring 容器从创建 Bean 实例开始，经过依赖注入、初始化，直到最终销毁的完整过程。
~~~

## BeanDefinition
~~~
Spring容器在进行实例化时，会将xml配置的<bean>的信息封装成一个BeanDefinition对象，Spring根据BeanDefinition来创建Bean对象，里面有很多的属性用来描述Bean

<bean id="userDao" class="com.dao.impl.UserDaolmpl" lazy-init="true"/>

<bean id="userService" class="com.service.UserServicelmpl" scope="singleton">
    <property name="userDao” ref="userDao”></property>
</bean>

beanClassName:bean 的类名
initMethodName:初始化方法名称
properryValues:bean 的属性值
scope:作用域
lazylnit:延迟初始化
~~~

## 流程 
~~~
1.BeanDefinition

bean的创建
    2.构造函数 
bean的初始化
    3.依赖注入
    4.Aware接口 （BeanNameAware   BeanFactoryAware  ApplicationContextAware）
    5.BeanPostProcessor#before 
    6.初始化方法（InitializingBean   自定义的init方法）
    7.BeanPostProcessor#after （bean的增强）(AOP  动态代理： 1.jdk动态代理  2.cglib动态代理)
bean的销毁



1、通过BeanDefinition获取bean的定义信息
2、调用构造函数实例化bean
3、bean的依赖注入（set方法）
4、处理Aware接口(BeanNameAware、 BeanFactoryAware、 ApplicationContextAware)
5、Bean的后置处理器BeanPostProcessor-前置
6、初始化方法(InitializingBean、init-method)
7、Bean的后置处理器BeanPostProcessor-后置
8、销毁bean
~~~

## 一、核心流程概览
~~~

实例化 (Instantiation)：容器创建 Bean 的实例对象（相当于 new 操作）。
属性填充 (Populate)：容器为 Bean 的属性赋值（依赖注入，DI）。
初始化 (Initialization)：执行各种初始化回调，使 Bean 处于可用状态。
使用 (In Use)：Bean 驻留在容器中，供应用程序使用。
销毁 (Destruction)：容器关闭时，清理资源并销毁 Bean。
~~~

## 二、详细步骤与扩展点（按执行顺序）
~~~
1. 实例化前 (Before Instantiation)
    BeanPostProcessor.postProcessBeforeInstantiation：
        这是生命周期中最早的扩展点。如果此方法返回一个对象，Spring 将直接使用该对象，跳过后续的实例化、属性填充等标准流程（常用于 AOP 代理的提前创建）。
2. 实例化 (Instantiation)
    通过构造函数、静态工厂方法或实例工厂方法创建 Bean 的原始对象。
3. 属性填充 (Populate Bean)
    依赖注入：Spring 解析 @Autowired、@Value、@Resource 等注解，通过反射将依赖注入到 Bean 的属性中。
4. 初始化前处理 (Before Initialization)
    BeanPostProcessor.postProcessBeforeInitialization：
        在初始化回调之前执行。常用于修改 Bean 的属性或进行自定义逻辑（例如 @Autowired 的注入检查就是在这里完成的）。
5. 初始化 (Initialization) - 关键阶段
    此阶段会按以下严格顺序执行三种类型的初始化操作：
        1.Aware 接口回调：
            如果 Bean 实现了 Aware 系列接口（如 BeanNameAware, BeanFactoryAware, ApplicationContextAware），容器会调用相应方法，注入容器自身的资源（如 Bean 名称、容器对象）。
        2.@PostConstruct 注解方法：
            这是 JSR-250 标准注解，优先级最高，由 CommonAnnotationBeanPostProcessor 处理。
        3.InitializingBean 接口方法：
            如果 Bean 实现了 InitializingBean 接口，调用其 afterPropertiesSet() 方法。
        4.自定义 init-method：
            如果在 XML 中配置了 init-method 或在 @Bean 注解中指定了 initMethod，最后调用该自定义方法。
                注意：如果有多个初始化方式，执行顺序是：@PostConstruct -> InitializingBean.afterPropertiesSet() -> init-method。
6. 初始化后处理 (After Initialization)
    BeanPostProcessor.postProcessAfterInitialization：
        在初始化完成后执行。AOP 动态代理通常就是在这里生成的。如果 Bean 需要被代理，这里会返回一个代理对象，替换原始 Bean 放入容器。
7. 使用中 (In Use)
    Bean 现在已完全准备好，驻留在 Spring 容器（单例池）中，等待被其他组件注入或直接调用。
8. 销毁 (Destruction)
    当容器关闭（如 ApplicationContext.close()）时，单例 Bean 会经历销毁过程，顺序如下：
        1.@PreDestroy 注解方法：
            JSR-250 标准注解，优先级最高。
        2.DisposableBean 接口方法：
            如果 Bean 实现了 DisposableBean 接口，调用其 destroy() 方法。
        3.自定义 destroy-method：
            调用配置的自定义销毁方法。
            注意：销毁顺序与初始化顺序相反。
~~~

## 三、生命周期流程图示 (简化版)
~~~
[开始] 
  ↓
[BeanPostProcessor.postProcessBeforeInstantiation] (可能直接返回代理，跳过后续)
  ↓
[实例化 (Constructor/Factory)]
  ↓
[属性填充 (@Autowired, @Value)]
  ↓
[BeanPostProcessor.postProcessBeforeInitialization]
  ↓
[Aware 接口回调 (BeanNameAware, etc.)]
  ↓
[@PostConstruct]
  ↓
[InitializingBean.afterPropertiesSet()]
  ↓
[Custom init-method]
  ↓
[BeanPostProcessor.postProcessAfterInitialization] (AOP 代理在此生成)
  ↓
[Bean 就绪，投入使用]
  ↓
(容器关闭时)
  ↓
[@PreDestroy]
  ↓
[DisposableBean.destroy()]
  ↓
[Custom destroy-method]
  ↓
[结束]
~~~

## AOP 是在哪个阶段生效的？
~~~
在 BeanPostProcessor.postProcessAfterInitialization 阶段。
这也是为什么 @PostConstruct 中调用的方法无法被 AOP 拦截的原因（因为此时代理还没创建，调用的是目标对象本身）。
如果需要 AOP 生效后的逻辑，应放在 postProcessAfterInitialization 之后的业务代码中，或者实现 SmartInitializingSingleton。


            // cglib代理对象
            Enhancer enhancer = new Enhancer();
            // 设置需要增强的类
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            // 执行回调方法，增强方法
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    // 执行目标方法
                    return methodProxy.invokeSuper(o, args);
                }
            });
            // 创建代理对象
            enhancer.create();            
~~~

## 原型 Bean (Prototype) 有销毁过程吗？
~~~
没有。Spring 容器只管理原型 Bean 的创建和依赖注入，一旦交付给调用者，容器就不再跟踪其生命周期，因此不会调用销毁回调。
如果需要管理原型 Bean 的销毁，需借助 DisposableBean 接口并在客户端手动管理，或使用 Bean 的后处理器。
~~~

## 循环依赖如何解决？
~~~
主要发生在属性填充阶段。
Spring 通过三级缓存（singletonFactories, earlySingletonObjects, singletonObjects）来解决单例 Bean 的 setter 循环依赖。
如果是构造器循环依赖，则无法自动解决，会抛出异常。
~~~


## Bean的创建顺序如何控制
~~~
Spring 容器会自动根据依赖关系（Dependency）来决定顺序，但在没有显式依赖的情况下，顺序是不确定的。

场景：
 A对象的属性有B对象，怎么保证B在A之前创建
 
 
1. 依赖注入（最推荐，隐式控制）
    Spring 最推崇的方式。如果 Bean A 依赖 Bean B，Spring 自然会先创建 Bean B。
        构造器注入：
            通过构造函数传递依赖，Spring 必须先实例化参数中的 Bean，才能实例化当前 Bean。
        Autowired 字段/Setter 注入：
            原理同上，Spring 在属性填充阶段会确保依赖的 Bean 已经存在。
        
@Component
public class BeanA {
    // Spring 会先创建 BeanB，再创建 BeanA
    public BeanA(BeanB beanB) {
        System.out.println("BeanA 初始化");
    }
}


2.@DependsOn 注解（显式强制控制）
@Component
@DependsOn("beanB") // 强制 spring 先初始化 beanB
public class BeanA {
    public BeanA() {
        System.out.println("BeanA 初始化");
    }
}

@Component
public class BeanB {
    public BeanB() {
        System.out.println("BeanB 初始化");
    }
}
注意：
    注解中的值必须是 Bean 的名称（默认是类名首字母小写，或者 @Component("customName") 指定的名字）。
    在 @Configuration 类中定义 @Bean 时也可以使用该注解。
    @DependsOn只适合一对一的场景
    
    
2.所有的bean对象都存在beanDefinitionMap
    实现BeanDefinitionRegistryPostProcessor接口
    钩子函数回调  postPostProcessBeanDefinitionRegistry()方法
        按照对象的顺序注册到IOC容器中
~~~