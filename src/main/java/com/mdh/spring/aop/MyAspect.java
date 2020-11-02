package com.mdh.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author MDH
 * 2020/11/1 14:52
 */
@Aspect
@Component
public class MyAspect {

    @Before("execution(public int com.mdh.spring.aop.CalcServiceImpl.*(..))")
    public void beforeNotify(){
        System.out.println("******* @Before我是前置通知MyAspect");
    }

    @After("execution(public int com.mdh.spring.aop.CalcServiceImpl.*(..))")
    public void afterNotify(){
        System.out.println("******* @After我是后置通知");
    }

    @AfterReturning("execution(public int com.mdh.spring.aop.CalcServiceImpl.*(..))")
    public void afterReturningNotify(){
        System.out.println("******* @AfterReturning我是返回后通知");
    }

    @AfterThrowing("execution(public int com.mdh.spring.aop.CalcServiceImpl.*(..))")
    public void afterThrowingNotify(){
        System.out.println("******* @AfterThrowing我是异常通知");
    }

    @Around("execution(public int com.mdh.spring.aop.CalcServiceImpl.*(..))")
    public Object aroundNotify(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object retValue = null;
        System.out.println("我是环绕通知之前AAA");
        retValue = proceedingJoinPoint.proceed();
        System.out.println("我是环绕通知之后BBB");
        return retValue;
    }
}
