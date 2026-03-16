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

~~~
1.异常捕获处理，手动处理了异常，没有抛出（只打印了异常）
    try {
            // 业务逻辑
            int a = 10 / 0;
            // 业务逻辑
        } catch (Exception e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
    原因：事务通知只有捕捉到了目标抛出的异常，才能进行后续的回滚处理，如果目标手动处理掉异常，事务会失效
    解决方式： 手动抛出异常，throw new RuntimeException("异常");
    
2.抛出检查异常,配置roobackFor属性为Exception
    @Transactional
    public void insert(Dto dto) throws FileNotFoundException {
        // 业务逻辑
        new FileInputStream("abc.txt");
        // 业务逻辑
    }

    原因：Spring默认只会回滚非检查异常
    解决方式： @Transactional(rollbackFor = Exception.class)
    
    
3.非public 方法
    原因：Spring为方法创建代理、添加事务通知的前提条件是该方法是public的
    解决方式：改为public
~~~