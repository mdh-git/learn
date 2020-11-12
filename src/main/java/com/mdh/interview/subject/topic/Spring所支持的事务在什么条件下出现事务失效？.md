# Spring事务失效

~~~
数据库myIsam不支持事务
@Transactional 注解在不是public方法上,接口上
@Transactional中的事务传递配置错误
@Transactional中注解的方法catch了异常
方法A引用支持事务的方法B,但是catch了方法B的异常,导致B事务失效
未配置事务管理器
为将事务配置类注入容器
~~~