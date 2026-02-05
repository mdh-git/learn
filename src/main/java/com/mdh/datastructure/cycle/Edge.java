package com.mdh.datastructure.cycle;

import lombok.Data;

import java.util.UUID;

/**
 * 线
 */
@Data
public class Edge {

    private String id;

    private Point start;

    private Point end;

    public Edge(Point start, Point end) {
        this.id = UUID.randomUUID().toString();
        this.start = start;
        this.end = end;
    }
}
