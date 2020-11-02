package com.mdh.spring.circulardepend.setinjection;


import org.springframework.stereotype.Component;

/**
 * @author MDH
 * 2020/11/2 21:12
 */
@Component
public class ServiceAA {

    private ServiceBB serviceBB;

    public void setServiceBB(ServiceBB serviceBB){
        this.serviceBB = serviceBB;
        System.out.println("A 里面设置了B");
    }
}
