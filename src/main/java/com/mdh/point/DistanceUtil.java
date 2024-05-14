package com.mdh.point;

import org.apache.commons.compress.utils.Lists;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DistanceUtil {

    /**
     * 操作多边形扩张/收缩
     *
     * @param points 待扩/收缩张多边形
     * @param dist   距离 正数扩张，负数收缩
     * @return 扩张/收缩后的多边形
     */
    public static List<Point> expansion(List<Point> points, double dist) {
        if (points.size() < 3) {
            throw new RuntimeException("not enough 3 point");
        }
        List<Point> npList = new LinkedList<>();
        List<Double> lngList = new LinkedList<>();
        List<Double> latList = new LinkedList<>();

        for (Point point : points) {
            lngList.add(point.getLongitude());
            latList.add(point.getLatitude());
        }
        Double minLng = Collections.min(lngList);
        Double minLat = Collections.min(latList);

        for (Point point : points) {
            Point p = new Point();
            p.setLongitude(point.getLongitude() - minLng);
            p.setLatitude(point.getLatitude() - minLat);
            npList.add(p);
        }

        List<Point> list = enlarge(npList, dist);
        LinkedList<Point> resultList = new LinkedList<>();
        for (Point point : list) {
            Point np = new Point();
            np.setLongitude(point.getLongitude() + minLng);
            np.setLatitude(point.getLatitude() + minLat);
            resultList.add(np);
        }

        return resultList;
    }

    /**
     * 多边形外扩/收缩
     */
    private static List<Point> enlarge(List<Point> points, double dist) {
        LinkedList<Point> resultList = new LinkedList<>();
        int size = points.size();
        double angle = dist * dmi();
        angle = isConvex(points) ? angle : -angle;

        for (int i = 0; i < size; i++) {
            Point pi = points.get(i);
            Point v1 = VectorUtil.sub(points.get((i - 1 + size) % size), pi);
            Point v2 = VectorUtil.sub(points.get((i + 1) % size), pi);
            double norm1 = VectorUtil.norm(v1);
            double norm2 = VectorUtil.norm(v2);

            if (norm1 <= 0 || norm2 <= 0) {
                // 剔除重复点（或距离极近的点）
                points.remove(i);
                return enlarge(points, dist);
            }

            Point normalizeV1 = VectorUtil.mul(v1, 1 / norm1);
            Point normalizeV2 = VectorUtil.mul(v2, 1 / norm2);
            double sinTheta = VectorUtil.cross(normalizeV1, normalizeV2);
            Point qi = VectorUtil.add(pi, VectorUtil.mul(VectorUtil.add(normalizeV1, normalizeV2), angle / sinTheta));
            resultList.add(qi);
        }

        return resultList;
    }

    /**
     * 计算1米代表的角度 这里只是近似计算，事实上随着纬度的变化其变动范围将受到影响
     */
    private static double dmi() {
        double s = 6378.137 * 1000;
        return 1 / (Math.PI / 180 * s);
    }

    /**
     * 判断是否凸多边形
     */
    private static boolean isConvex(List<Point> points) {
        double area = 0;
        Point p1 = VectorUtil.sub(points.get(1), points.get(0));
        Point p2;
        for (int i = 2; i < points.size(); i++) {
            p2 = VectorUtil.sub(points.get(i), points.get(0));
            area += VectorUtil.cross(p1, p2);
            p1 = p2;
        }
        return area > 0;
    }

    /**
     * 多边形周长
     */
    private static double perimeter(List<Point> points) {
        int size = points.size();
        double perimeter = 0.0d;
        Point point;
        for (int i = 0; i < size; i++) {
            point = points.get(i);
            perimeter += VectorUtil.norm(VectorUtil.sub(points.get((i - 1 + size) % size), point));
        }
        return perimeter;
    }


    public static void main(String[] args) {
        List<Point> points = Lists.newArrayList();

        Point point0 = new Point(2239.9761,2249.4295);
        Point point1 = new Point(2760.5068,2249.4295);
        Point point2 = new Point(2760.5068,1983.8388);
        Point point3 = new Point(2239.9761,1983.8388);

        points.add(point0);
        points.add(point1);
        points.add(point2);
        points.add(point3);

        for (Point point : points) {
            System.out.println(point.getLongitude() + "\t " + point.getLatitude());
        }

        double dist = 400000;
        List<Point> expansion = expansion(points, dist);

        for (Point point : expansion) {
            System.out.println(point.getLongitude() + "\t " + point.getLatitude());
        }
    }
}
