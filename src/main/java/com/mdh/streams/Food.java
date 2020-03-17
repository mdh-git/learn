package com.mdh.streams;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author madonghao
 * @create 2020-03-17 15:06
 **/
@Data
@AllArgsConstructor
public class Food {
    List<Fruit> fruitList;
    List<Meat> meatList;
}
