package com.mdh.polygon;

import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class GraphDemo {

    public static void main(String[] args) {
        Graph graph = initGraph();

        List<Point> list = buildGraph(graph);
    }

    private static List<Point> buildGraph(Graph graph) {
        List<Point> list = Lists.newArrayList();

        List<Point> curList = graph.getCurList();
        int size = curList.size();
        for (int i = 0; i < size; i++) {
            int pre;
            int next = i + 1;
            if(i == 0){
                pre = size - 1;
            } else {
                pre = i - 1;
            }
            if(next == size){
                next = 0;
            } else {
                next = i + 1;
            }

            Point point = buildPoint(curList.get(pre), curList.get(i), curList.get(next));
        }


        return list;
    }

    private static Point buildPoint(Point pre, Point cur, Point next) {
        Point point = new Point();

        double preX = pre.getX();
        double preY = pre.getY();

        double curX = cur.getX();
        double curY = cur.getY();

        double nextX = next.getX();
        double nextY = next.getY();



        return point;
    }

    private static Graph initGraph() {
        Graph graph = new Graph();

        List<Point> curList = Lists.newArrayList();
        List<Point> newList = Lists.newArrayList();

        Point point0 = new Point(1,2);
        Point point1 = new Point(1,1);
        Point point2 = new Point(2,1);
        Point point3 = new Point(2,-1);
        Point point4 = new Point(1,-1);
        Point point5 = new Point(1,-2);
        Point point6 = new Point(-1,-2);
        Point point7 = new Point(-2,-1);
        Point point8 = new Point(-2,1);
        Point point9 = new Point(-1,1);

        curList.add(point0);
        curList.add(point1);
        curList.add(point2);
        curList.add(point3);
        curList.add(point4);
        curList.add(point5);
        curList.add(point6);
        curList.add(point7);
        curList.add(point8);
        curList.add(point9);

        graph.setCurList(curList);
        graph.setNewList(newList);
        return graph;
    }
}
