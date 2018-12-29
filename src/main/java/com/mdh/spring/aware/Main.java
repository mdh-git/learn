package com.mdh.spring.aware;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 运行
 * @Author: madonghao
 * @Date: 2018/12/27 19:34
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AwareConfig.class);
        AwareService awareService = context.getBean(AwareService.class);
        awareService.outputResult();

        context.close();
    }
}
