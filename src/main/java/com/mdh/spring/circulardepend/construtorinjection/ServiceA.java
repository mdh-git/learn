package com.mdh.spring.circulardepend.construtorinjection;

import org.springframework.stereotype.Component;

/**
 * @author MDH
 * 2020/11/2 21:05
 */
@Component
public class ServiceA {

    private ServiceB serviceB;

    public ServiceA(ServiceB serviceB) {
        this.serviceB = serviceB;
    }
}
