package com.mdh.spring.circulardepend.construtorinjection;

import org.springframework.stereotype.Component;

/**
 * @author MDH
 * 2020/11/2 21:06
 */
@Component
public class ServiceB {

    private ServiceA serviceA;

    public ServiceB(ServiceA serviceA) {
        this.serviceA = serviceA;
    }
}
