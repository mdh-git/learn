package com.mdh.spring.circulardepend.setinjection;

/**
 * @author MDH
 * 2020/11/2 21:12
 */
public class SetterTest {
    public static void main(String[] args) {
        ServiceAA serviceAA = new ServiceAA();
        ServiceBB serviceBB = new ServiceBB();

        serviceAA.setServiceBB(serviceBB);
        serviceBB.setServiceAA(serviceAA);
    }
}
