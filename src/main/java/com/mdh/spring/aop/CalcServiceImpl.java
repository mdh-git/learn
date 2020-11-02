package com.mdh.spring.aop;

import org.springframework.stereotype.Service;

/**
 * @author MDH
 * 2020/11/1 14:49
 */
@Service
public class CalcServiceImpl implements CalcService {

    @Override
    public int div(int x, int y) {
        int result = x/y;
        System.out.println("    =========CalcServiceImpl被调用了,计算结果:" + result);
        return result;
    }
}
