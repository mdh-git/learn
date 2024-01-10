package com.mdh.guava;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {
    private Integer id;
    private String name;
    private String sex;
}
