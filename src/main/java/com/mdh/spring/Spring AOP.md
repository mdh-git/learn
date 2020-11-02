# Spring AOP

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