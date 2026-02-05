package com.mdh.datastructure.cycle;

import lombok.Data;

import java.util.UUID;

/**
 * 点
 */
@Data
public class Point {

    private String id;

    private Double x;

    private Double y;

    public Point(Double x, Double y) {
        this.id = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
    }
}
