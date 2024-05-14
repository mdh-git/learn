package com.mdh.polygon;

import lombok.Data;

import java.util.List;

@Data
public class Graph {

    private List<Point> preList;

    private List<Point> curList;

    private List<Point> newList;

    private List<Point> nextList;
}
