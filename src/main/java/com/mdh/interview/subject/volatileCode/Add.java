package com.mdh.interview.subject.volatileCode;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Add {
    public static void main(String[] args) {
        System.out.println(1);

        BigDecimal bigDecimal = new BigDecimal(1);
        if(bigDecimal == null){
            System.out.println(bigDecimal.toString());
        }

        boolean aNull = StringUtils.isNotBlank("null");
        System.out.println(aNull);

        System.out.println();

        Long aLong = Long.valueOf("");
        System.out.println(aLong);
    }
    volatile int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    public void add(){
        num++;
    }

    public void atomicAdd(){
        atomicInteger.incrementAndGet();
    }

}
