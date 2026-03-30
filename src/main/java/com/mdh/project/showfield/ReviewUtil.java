package com.mdh.project.showfield;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class ReviewUtil {
    public static Object getField(Object o, String name) {
        if (o == null) return null;
        try {
            Field field = o.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(o);
        } catch (Exception e) {
            log.error("获取属性失败，{}，{},{}", name, o.getClass().getName(), e.getMessage(), e);
            return null;
        }
    }
}
