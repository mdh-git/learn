package com.mdh.util;

import org.apache.fury.Fury;
import org.apache.fury.config.Language;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class FuryUtil {

    private static final Set<String> BASE_CLASS_TYPE = new HashSet<>(Arrays.asList("byte", "short", "int", "long", "float", "double", "boolean", "char", "void"));

    private static Fury getFury() {
        return Fury.builder().withLanguage(Language.JAVA).requireClassRegistration(false).withJdkClassSerializableCheck(false).withRefTracking(true).build();
    }

    /**
     * 序列化
     * @param object 对象
     * @return
     */
    public static byte[] serialize(Object object) {
        Class<?> clazz = object.getClass();
        Fury fury = getFury();
        List<Class<?>> clazzSet = getAllClass(clazz);
        for (Class clazz1 : clazzSet) {
            fury.register(clazz1);
        }
        return fury.serialize(object);
    }

    /**
     * 反序列化
     * @param bytes 序列化的byte数组
     * @param clazz 反序列化的文件class
     * @return
     */
    public static <T> T deserialize(byte[] bytes, Class<?> clazz) {
        Fury fury = getFury();
        List<Class<?>> clazzSet = getAllClass(clazz);
        for (Class<?> clazz1 : clazzSet) {
            fury.register(clazz1);
        }
        return (T) fury.deserialize(bytes);
    }

    /**
     * 输出所有除基础类型外的class
     * @param clazz
     * @return
     */
    private static List<Class<?>> getAllClass(Class<?> clazz) {
        List<Class<?>> clazzSet = new ArrayList<>();
        appendClass(clazz, clazzSet);
        return clazzSet;
    }

    /**
     * 输出所有除基础类型外的class （递归）
     * @param clazz
     * @param clazzSet
     */
    private static void appendClass(Class<?> clazz, List<Class<?>> clazzSet) {
        if (clazzSet.contains(clazz)) {
            return;
        }
        if (clazz.getName().startsWith("java.lang") || BASE_CLASS_TYPE.contains(clazz.getName())) {
            return;
        }
        clazzSet.add(clazz);
        // 找父类
        if (clazz.getSuperclass() != null) {
            appendClass(clazz.getSuperclass(), clazzSet);
        }
        // 获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            // 判断是否有泛型
            if (hasGenericType(field)) {
                Type genericType = field.getGenericType();
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type typeArgument : actualTypeArguments) {
                    if (typeArgument instanceof Class) {
                        appendClass((Class<?>) typeArgument, clazzSet);
                    }
                }
            } else {
                appendClass((Class<?>) field.getType(), clazzSet);
            }
        }
    }

    /**
     * 字段是否有泛型
     * @param field
     * @return
     */
    private static boolean hasGenericType(Field field) {
        Type type = field.getGenericType();
        return type != null && !(type instanceof Class);
    }

}
