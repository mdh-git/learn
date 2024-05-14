package com.mdh.polygon;

import lombok.Data;

@Data
public class Point {

    private double x;

    private double y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }
}
