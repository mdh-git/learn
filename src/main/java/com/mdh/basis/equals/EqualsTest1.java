package com.mdh.basis.equals;

/**
 * @author madonghao
 * @create 2020-05-12 11:04
 **/
public class EqualsTest1 {
    public static void main(String[] args) {
        // 新建2个相同内容的Person对象，
        // 再用equals比较它们是否相等
        Person p1 = new Person("eee", 100);
        Person p2 = new Person("eee", 100);
        System.out.printf("%s\n", p1.equals(p2));
    }


    private static class Person {
        int age;
        String name;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + " - " +age;
        }
    }
}
