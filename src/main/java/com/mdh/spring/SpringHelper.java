package com.mdh.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: madonghao
 * @Date: 2018/12/28 12:46
 */
@Component
public class SpringHelper implements ApplicationContextAware{
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringHelper.applicationContext = applicationContext;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T getBean(Class clazz){
        return (T) applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String className){
        return (T) applicationContext.getBean(className);
    }
}
