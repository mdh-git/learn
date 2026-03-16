# MyBatis执行流程
~~~
MyBatis 的执行流程可以概括为：配置加载 -> 会话创建 -> SQL 解析与封装 -> 执行器调度 -> JDBC 交互 -> 结果映射。
~~~

~~~
1.读取MyBatis配置文件:mybatis-config.xml加载运行环境和映射文件
2.构造会话工厂SqlSessionFactory
3.会话工厂创建SqlSession对象(包含了执行SQL语句的所有方法)
4.操作数据库的接口，Executor执行器，同时负责查询缓存的维护
5.Executor接口的执行方法中有一个MappedStatement类型的参数，封装了映射信息
6.输入参数映射
7.输出结果映射
~~~


## MyBatis支持延迟加载
~~~
MyBatis支持延迟加载，默认是不开启的
1.fetchType = lazy 针对局部设置
2.在mybatis-config.xml的settings中添加 <Setting name = "lazyLoadingEnable" value = "true">

<configuration>
    <settings>
        <!-- 1. 开启延迟加载的全局开关 (默认 false) -->
        <setting name="lazyLoadingEnabled" value="true"/>
        
        <!-- 2. 设置是否任何方法调用都会触发加载 (默认 true)
             true: 调用对象的任何方法(如 getName())都会触发加载所有关联属性
             false: 只有调用关联属性本身(如 getOrderList())时才触发加载 (推荐) -->
        <setting name="aggressiveLazyLoading" value="false"/>
        
        <!-- 可选：指定哪些方法触发加载 (逗号分隔)，默认包含 equals, clone, hashCode, toString -->
        <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
    </settings>
</configuration>
~~~

## 问题
~~~
1.#{} 和 $ {} 的区别？
    #{}：预编译处理（PreparedStatement），MyBatis 会将其替换为 ?，能有效防止 SQL 注入。
    ${}：字符串替换，直接拼接到 SQL 中，存在注入风险，常用于动态表名或排序字段。
2.一级缓存和二级缓存？
    本地缓存，基于PerpetualCache，本质是一个HashMap
    一级缓存：默认开启，基于 SqlSession，会话关闭或执行增删改后清空。
    二级缓存：默认是关闭的，基于 Namespace 和 mapper ，需手动配置，多个 Session 共享，通常在分布式环境下建议关闭（改用 Redis 等集中式缓存）。
            <setting name="cacheEnabled" value="true"/> 在对应的xml文件中加入 <cache/> 标签
            
    当某一个作用域（一级缓存Session/二级缓存NameSpaces）进行了增删改操作后，默认该作用域下所有select中的缓存会被清除。
    
    
3.MyBatis 如何实现动态代理？
    通过 MapperProxyFactory 利用 JDK 动态代理生成接口的实现类，拦截方法调用并转换为 SqlSession 的 execute 操作。
    默认使用的是 JDK 动态代理，在需要被代理的时候使用cglib
~~~