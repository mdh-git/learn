# Spring AOP

~~~
核心： 使用Aop中的环绕通知 + 切点表达式（找到要记录日志的方法），通过环绕通知的参数获取请求方法的参数（类、方法、注解、请求方式）
~~~

## AOP的通知
~~~
@Before
    前置通知: 目标方法之前执行

@After
    后置通知: 目标方法之后执行(始终执行)

@AfterReturning
    返回通知: 执行方法结束前执行(异常不执行)

@AfterThrowing
    异常通知: 出现异常是时候执行

@Around
    环绕通知: 环绕目标方法执行
~~~

## Spring4的通知顺序
~~~
正常执行:
我是环绕通知之前AAA
******* @Before我是前置通知MyAspect
    =========CalcServiceImpl被调用了,计算结果: 5
我是环绕通知之后BBB
******* @After我是后置通知
******* @AfterReturning我是返回后通知

异常执行:
我是环绕通知之前AAA
******* @Before我是前置通知MyAspect
******* @After我是后置通知
******* @AfterThrowing我是异常通知

正常执行:@Before  @After  @AfterReturning
异常执行:@Before  @After  @AfterThrowing
~~~

## Spring5的通知顺序
~~~
正常执行:
我是环绕通知之前AAA
******* @Before我是前置通知MyAspect
    =========CalcServiceImpl被调用了,计算结果: 5
******* @AfterReturning我是返回后通知
******* @After我是后置通知
我是环绕通知之后BBB

异常执行:
我是环绕通知之前AAA
******* @Before我是前置通知MyAspect
******* @AfterThrowing我是异常通知
******* @After我是后置通知

正常执行:@Before  @AfterReturning  @After
异常执行:@Before  @AfterThrowing  @After

try {
    @Before
    method.invoke(obj, args);
    @AfterReturning
} catch(){
    @AfterThrowing
} finally{
    @After
}
~~~

## Spring AOP 什么场景下会失效？
~~~
1.内部方法调用失败
    原因：OP通过代理对象增强方法，但内部调用通过this直接调用目标对象的方法，绕过了代理
    解决：使用AopContext.currentProxy() 需开启exposeProxy配置
    
2，非Spring管理的对象
    通过new关键字直接创建对象，而非通过Spring容器获取Bean:
    原因：AOP只能代理Spring容器管理的Bean
    解决方案：使用@Componet等注解将类声明为Bean，并通过@Autowried注入
    
3，异步方法（@Async）
    原因：异步线程上下文与代理分离，导致切面逻辑失败
    解决方案：在异步方法外层调用处添加AOP逻辑，或使用线程本地变量传递上下文
    
4.切入点表达式错误
    表达式未正确匹配目标方法
    解决方案： 使用精切的表达式，如execution(* com.example.service.UserService.*(..))
~~~