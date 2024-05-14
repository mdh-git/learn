package com.mdh.point;

public class VectorUtil {

    /**
     * 向量求模数
     */
    public static double norm(Point point) {
        return Math.sqrt(point.getLongitude() * point.getLongitude() + point.getLatitude() * point.getLatitude());
    }

    /**
     * 向量加法
     */
    public static Point add(Point point1, Point point2) {
        Point point = new Point();
        point.setLongitude(point1.getLongitude() + point2.getLongitude());
        point.setLatitude(point1.getLatitude() + point2.getLatitude());
        return point;
    }

    /**
     * 向量减法
     */
    public static Point sub(Point point1, Point point2) {
        Point point = new Point();
        point.setLongitude(point1.getLongitude() - point2.getLongitude());
        point.setLatitude(point1.getLatitude() - point2.getLatitude());
        return point;
    }

    /**
     * 向量乘法
     */
    public static Point mul(Point p, double d) {
        Point point = new Point();
        point.setLongitude(p.getLongitude() * d);
        point.setLatitude(p.getLatitude() * d);
        return point;
    }

    /**
     * 向量叉乘
     */
    public static double cross(Point p1, Point p2) {
        return p1.getLongitude() * p2.getLatitude() - p1.getLatitude() * p2.getLongitude();
    }
}
