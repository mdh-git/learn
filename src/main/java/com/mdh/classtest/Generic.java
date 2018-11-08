package com.mdh.classtest;

/**
 *
 * 此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
 * 在实例化泛型类时，必须指定T的具体类型
 * @author madonghao
 * @date 2018/11/5
 */
public class Generic<T> {
    /** key这个成员变量的类型为T,T的类型由外部指定 */
    private T key;

    /** 泛型构造方法形参key的类型也为T，T的类型由外部指定 */
    public T getKey() {
        return key;
    }

    /** 泛型方法getKey的返回值类型为T，T的类型由外部指定 */
    public void setKey(T key) {
        this.key = key;
    }

    public static void main(String[] args) {
        /**
         * 泛型的类型参数只能是类类型（包括自定义类），不能是简单类型
         * 传入的实参类型需与泛型的类型参数类型相同，即为Integer.
         */
        Generic<Integer> genericInterger = new Generic<Integer>();
        genericInterger.setKey(123456);

        /** 传入的实参类型需与泛型的类型参数类型相同，即为String. */
        Generic<String> genericString = new Generic<String>();
        genericString.setKey("key_value");

        System.out.println("泛型测试,key is " + genericInterger.getKey());
        System.out.println("泛型测试,key is " + genericString.getKey());
    }
}
