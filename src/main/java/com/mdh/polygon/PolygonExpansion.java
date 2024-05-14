package com.mdh.polygon;

import java.util.ArrayList;
import java.util.List;

public class PolygonExpansion {
    public static List<Point> expandPolygon(List<Point> polygon, double distance) {
        List<Point> expandedPolygon = new ArrayList<>();

        // 假设多边形是封闭的，否则需要先闭合多边形
        for (int i = 0; i < polygon.size(); i++) {
            Point current = polygon.get(i);
            Point next = polygon.get((i + 1) % polygon.size());

            // 计算当前边的方向向量
            Point direction = new Point(next.x - current.x, next.y - current.y);
            double directionLength = Math.sqrt(direction.x * direction.x + direction.y * direction.y);
            direction.x /= directionLength;
            direction.y /= directionLength;

            // 计算法向量
            Point normal = new Point(direction.y, -direction.x);

            // 计算扩展后的点坐标
            Point expandedCurrent = new Point(
                    (float) (current.x + distance * normal.x),
                    (float) (current.y + distance * normal.y)
            );
            Point expandedNext = new Point(
                    (float) (next.x + distance * normal.x),
                    (float) (next.y + distance * normal.y)
            );


            // 添加新边的两个端点到扩展后的多边形中
            expandedPolygon.add(expandedCurrent);
            expandedPolygon.add(expandedNext);

            //expandedPolygon.add(new Point((expandedCurrent.x + expandedNext.x) / 2, (expandedCurrent.y + expandedNext.y) / 2));

            // 如果需要，还可以添加原多边形内部的点
        }

        return expandedPolygon;
    }

    public static void main(String[] args) {
        // 示例多边形
        List<Point> polygon = new ArrayList<>();
        polygon.add(new Point(2239.9761,2249.4295));
        polygon.add(new Point(2760.5068,2249.4295));
        polygon.add(new Point(2760.5068,1983.8388));
        polygon.add(new Point(2239.9761,1983.8388));

        for (Point point : polygon) {
            System.out.println(point.x + "\t " + point.y);
        }

        // 多边形外扩1单位长度
        List<Point> expandedPolygon = expandPolygon(polygon, 2);

        // 输出扩展后的多边形
        for (Point point : expandedPolygon) {
            System.out.println(point.x + "\t " + point.y);
        }
    }

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // 根据需要添加其他必要方法
    }
}
