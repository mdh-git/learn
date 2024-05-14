package com.mdh.point;

public class Translation {
    public static void main(String[] args) {
        double x = 10; // 原点x坐标
        double y = 10; // 原点y坐标
        double angle = Math.toRadians(225); // 角度转换为弧度
        double distance = 10; // 外平移的距离

        double xTranslated = translateX(x, y, angle, distance);
        double yTranslated = translateY(x, y, angle, distance);

        System.out.println("Translated Point: (" + xTranslated + ", " + yTranslated + ")");
    }

    // 角度转换为弧度
    public static double toRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    // X轴外平移
    public static double translateX(double x, double y, double angle, double distance) {
        return x + Math.cos(angle) * distance;
    }

    // Y轴外平移
    public static double translateY(double x, double y, double angle, double distance) {
        return y + Math.sin(angle) * distance;
    }
}
