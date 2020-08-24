package com.mdh.interview.subject.casCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User user1 = new User("张三", 22);
        User user2 = new User("李四", 25);

        atomicReference.set(user1);
        System.out.println(atomicReference.compareAndSet(user1, user2) + "\t" + atomicReference.get().toString());

        System.out.println(atomicReference.compareAndSet(user1, user2) + "\t" + atomicReference.get().toString());
    }
}

@Getter
@AllArgsConstructor
class User{
    private String userName;
    private Integer age;
}
