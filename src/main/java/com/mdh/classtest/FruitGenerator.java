package com.mdh.classtest;

import java.util.Random;

/**
 *
 * @author madonghao
 * @date 2018/11/5
 */
public class FruitGenerator implements IGeneric<String> {

    private String[] fruits = new String[]{"Apple", "Banana", "Pear"};

    @Override
    public String next() {
        Random random = new Random();
        return fruits[random.nextInt(3)];
    }

    public static void main(String[] args) {
        FruitGenerator fruitGenerator = new FruitGenerator();
        System.out.println(fruitGenerator.next());
    }
}


