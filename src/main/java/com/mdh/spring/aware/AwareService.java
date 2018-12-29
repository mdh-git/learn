package com.mdh.spring.aware;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Spring Aware演示Bean
 *
 * @Author: madonghao
 * @Date: 2018/12/27 19:18
 */
@Service
public class AwareService implements BeanNameAware,ResourceLoaderAware{//实现BeanNameAware,ResourceLoaderAware接口,获取Bean名称和支援加载的服务

    private String beanName;
    private ResourceLoader loader;

    @Override
    public void setBeanName(String name) {//实现BeanNameAware需要重写setBeanName方法
        this.beanName = name;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {//实现ResourceLoaderAware需要重写setResourceLoader方法
        this.loader = resourceLoader;
    }

    public void outputResult(){
        System.out.println("Bean的名称:" + beanName);
        Resource resource = loader.getResource("classpath:com/mdh/spring/spring.txt");
        try {
            System.out.println("ResourceLoader加载的文件内容为:" + IOUtils.toString(resource.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
