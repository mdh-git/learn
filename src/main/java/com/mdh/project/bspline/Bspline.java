package com.mdh.project.bspline;

import cn.hutool.core.collection.CollUtil;
import com.mdh.project.Point;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * B样条曲线
 */
public class Bspline {

    public static void main(String[] args) {
        List<Point> splinePoints = Lists.newArrayList();

        List<Point> out = GetBspline(splinePoints, 50, 4);
    }

    /**
     * 获取b样条的线段点
     * @param controlPoints 控制点
     * @param detail 要采样的曲线点个数
     * @param order 次数=（阶数-1）
     * @return
     */
    public static List<Point> GetBspline(List<Point> controlPoints, int detail, int order) {
        List<Point> points = com.google.common.collect.Lists.newArrayList();
        if (order < 4)
            order = controlPoints.size() <= 4 ? controlPoints.size() - 1 : 4;
        // 生成knots
        float[] knots = createKnots(controlPoints.size(), order);
        // 根据[k-1,n+1)这个区间长度和采样的线段点detail个数生成t的增量
        float tJump = (knots[knots.length - order] - knots[order - 1]) / (detail - 1);
        float t;
        Point point;
        for (int i = 0; i < detail; i++) {
            if (i == detail - 1) {
                point = controlPoints.get(controlPoints.size() - 1);
            } else {
                //获取t
                t = knots[order - 1] + i * tJump;
                // 根据t获取节点向量的下标
                int tInt = whichInterval(t, knots);
                if (tInt >= controlPoints.size())
                    continue;
                // 执行deboor算法
                point = DeBoor(order - 1, order, tInt, t, controlPoints, knots);
            }
            points.add(point);
        }

        return points;
    }

    public static Point DeBoor(int j, int k, int i, float t, List<Point> controlPoints, float[] knots) {
        // 如果j==0，就返回控制点
        if (j == 0)
            return controlPoints.get(i);
        else {
            //pi = （t-ti)/(t_{i+k-j} - ti)
            float param = (t - knots[i]) / (knots[i + k - j] - knots[i]);
            // （1-pi）*deboor（j-1,k,i-1)+ pi*deboor(j-1,k,i)
            Point point1 = DeBoor(j - 1, k, i - 1, t, controlPoints, knots);
            Point point2 = DeBoor(j - 1, k, i, t, controlPoints, knots);
            return new Point((1 - param) * point1.x + param * point2.x, (1 - param) * point1.y + param * point2.y);
        }
    }

    public static float[] createKnots(int nControl, int order) {
        // 节点数= 控制点数+次数（或者是阶数+1）n+k
        int nKnots = nControl + order;

        float[] knots = new float[nKnots];
        for (int i = 0; i < nKnots; i++) {
            //除了k-1，n+1 这个区间，其他重复度为k，这是一个clamped B样条。
            if (i < order) {
                knots[i] = 0;
            }
            //n+k-k+1 = n+1
            else if (i < nKnots - order + 1) {
                knots[i] = knots[i - 1] + 1;
            } else {
                knots[i] = knots[i - 1];
            }
        }
        return knots;
    }

    public static int whichInterval(float t, float[] knots) {
        for (int i = 1; i < knots.length - 1; i++) {
            if (t < knots[i])
                return (i - 1);
            else if (t == knots[knots.length - 1])
                return (knots.length - 1);
        }
        return -1;
    }
}
