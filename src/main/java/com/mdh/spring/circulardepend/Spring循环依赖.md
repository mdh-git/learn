# Spring的循环依赖
~~~
多个bean之间相互依赖,形成一个闭环。
比如:A依赖于B,B依赖于C,C依赖于A

通常来说,如果问Spring容器内部如何解决循环依赖,一定是指默认的单例Bean中,属性相互引用的场景。
~~~

## 依赖注入
~~~
1. 构造方法注入(不建议,Spring官网介绍 会引起循环依赖的问题)
    SpringIOC在运行时会检测到循环依赖,抛出BeanCurrentlyInCreateionException
2. Setter方法注入

AB循环依赖问题只要A的注入方式是setter且singleton,就不会有循环依赖的问题
~~~

## Spring容器中的循环依赖
~~~
Spring默认的Bean都是单例的
默认的单例(singleton)的场景是支持循环依赖的,不会报错
原型(Prototype)的场景是不支持循环依赖的,会报错
~~~

## spring内部通过3级缓存来解决循环依赖问题
~~~
DefaultSingletonBeanRegistry类来解决循环依赖问题
只有单例的Bean会通过三级缓存提前暴露来解决循环依赖的问题,而非单例的bean,每次从容器中
获取都是一个新的对象,都会重新创建,所以非单例的Bean是没有缓存的,不会将其放到三级缓存中。

(成品)第一级缓存(也叫单例池)singletonObjects: 存放已经经历了完整生命周期的Bean的对象
(半成品)第二级缓存: earlySingletonObjects  存放早起暴露出来的Bean对象,Bean的生命周期未结束(属性还未填充完整)
(工厂,准备构建的)第三级缓存: Map<String, ObjectFactory<?>> singletonFactories,存放可以生成Bean的工厂


~~~
~~~
实例化: 分配内存空间
初始化: 属性赋值


public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

一级缓存 singletonObjects 存放的是已经初始化好le的Bean
	/** Cache of singleton objects: bean name to bean instance. */
单例对象的缓存: bean名称-bean实例,即:所谓的单例池。
表示已经经历了完整生命周期的Bean对象
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

三级缓存 singletonFactories存放的是FactoryBean。假如A类实现了FactoryBean,那么依赖注入的时候不是A类,而是A类产生的Bean
	/** Cache of singleton factories: bean name to ObjectFactory. */
单利工厂的高速缓存:bean名称-ObjectFactory
表示存放生成Bean的工厂
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);


二级缓存 earlySingletonObjects存放的是实例化了,但是未初始化的Bean
	/** Cache of early singleton objects: bean name to bean instance. */
早起的单例对象的高速缓存:bean名称 -- bean实例。
表示Bean的生命周期还没走完(Bean的属性还未填充)就把这个Bean存入改缓存中
也就是实例化但未初始化的bean放入改缓存里
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
}


3个map 和 4个方法
singletonObjects        singletonFactories      earlySingletonObjects

getSingleton    doCreateBean        populateBean        addSingleton
~~~
~~~
A/B两对象在三级缓存中的迁移说明

(1): A创建过程中需要B,于是A将自己放到三级缓存里,去实例化B
(2): B实例化的时候发现需要A,于是B先查一级缓存,没有,在查二级缓存,没有,在查三级缓存有,
     找到了A然后把三级缓存里面的这个A放到二级缓存里面,并删除三级缓存里面的A
(3): B顺利初始化完毕,将自己放到一级缓存里面(此时B里面的A依然是创建中状态)
      然后回来接着创建A,此时B已经结束创建,直接从一级缓存里面拿到B,然后完成创建,并将A自己放到一级缓存里面。
~~~