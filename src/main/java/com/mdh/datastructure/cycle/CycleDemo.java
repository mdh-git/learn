package com.mdh.datastructure.cycle;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class CycleDemo {


    public static void main(String[] args) {
        Graph graph = getGraph();

//        Optional<List<Edge>> cycle = graph.findCycle();
//        if (cycle.isPresent()) {
//            System.out.println("找到环，包含以下边：");
//            for (Edge e : cycle.get()) {
//                System.out.println("  " + e.getStart().getId() + " —— " + e.getEnd().getId());
//            }
//        } else {
//            System.out.println("图中无环");
//        }


        GraphOne graph1 = getGraph1();
        List<Edge> cycleEdges = graph1.findAllCycleEdges();

        System.out.println("属于环的边（非桥边）共 " + cycleEdges.size() + " 条：");
        for (Edge e : cycleEdges) {
            System.out.println("  " + e.getStart().getId().substring(0, 5) + " —— " + e.getEnd().getId().substring(0, 5));
        }
    }

    @NotNull
    private static Graph getGraph() {
        Point A = new Point( 0.0, 0.0);
        Point B = new Point(1.0, 0.0);
        Point C = new Point(1.0, 1.0);
        Point D = new Point(0.0, 1.0);

        // 构成一个四边形环
        Edge e1 = new Edge(A, B);
        Edge e2 = new Edge(B, C);
        Edge e3 = new Edge(C, D);
        Edge e4 = new Edge(D, A);
        // 对角线（可选）
        Edge e5 = new Edge(A, C);

        Graph graph = new Graph();
        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);
        return graph;
    }

    @NotNull
    private static GraphOne getGraph1() {
        Point A = new Point(0.0, 0.0);
        Point B = new Point(1.0, 0.0);
        Point C = new Point(1.0, 1.0);
        Point D = new Point(0.0, 1.0);
        Point E = new Point(2.0, 0.0);
        Point F = new Point(2.0, 1.0);

        // 环1: A-B-C-D-A
        Edge e1 = new Edge(A, B);
        Edge e2 = new Edge(B, C);
        Edge e3 = new Edge(C, D);
        Edge e4 = new Edge(D, A);

        // 环2: B-E-F-C-B
        Edge e5 = new Edge(B, E);
        Edge e6 = new Edge(E, F);
        Edge e7 = new Edge(F, C);

        // 连接两个环的两条边：B-C 已存在，再加一条？比如 A-F（可选）
        // 但即使不加，B-C 是共享边，两个环已通过 B-C 连通

        GraphOne g = new GraphOne();
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        return g;
    }
}
