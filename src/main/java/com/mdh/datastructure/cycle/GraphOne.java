package com.mdh.datastructure.cycle;

import java.util.*;

public class GraphOne {
    private Map<Point, List<Edge>> adj = new HashMap<>(); // 邻接表存 Edge，方便回溯
    private Set<Edge> allEdges = new HashSet<>();

    public void addEdge(Edge edge) {
        Point u = edge.getStart();
        Point v = edge.getEnd();

        adj.computeIfAbsent(u, k -> new ArrayList<>()).add(edge);
        adj.computeIfAbsent(v, k -> new ArrayList<>()).add(edge);
        allEdges.add(edge);
    }

    /**
     * 返回图中所有属于至少一个环的边（即：非桥边）
     */
    public List<Edge> findAllCycleEdges() {
        Set<Edge> bridges = findBridges();
        List<Edge> cycleEdges = new ArrayList<>();
        for (Edge e : allEdges) {
            if (!bridges.contains(e)) {
                cycleEdges.add(e);
            }
        }
        return cycleEdges;
    }

    // Tarjan 找桥
    private Set<Edge> findBridges() {
        Set<Edge> bridges = new HashSet<>();
        Map<Point, Integer> disc = new HashMap<>(); // discovery time
        Map<Point, Integer> low = new HashMap<>();
        Map<Point, Boolean> visited = new HashMap<>();
        int[] time = {0};

        for (Point v : adj.keySet()) {
            if (!visited.getOrDefault(v, false)) {
                dfsForBridges(v, null, disc, low, visited, time, bridges);
            }
        }
        return bridges;
    }

    private void dfsForBridges(
            Point u,
            Edge parentEdge,
            Map<Point, Integer> disc,
            Map<Point, Integer> low,
            Map<Point, Boolean> visited,
            int[] time,
            Set<Edge> bridges) {

        visited.put(u, true);
        disc.put(u, time[0]);
        low.put(u, time[0]);
        time[0]++;

        for (Edge e : adj.getOrDefault(u, Collections.emptyList())) {
            Point v = e.getStart().equals(u) ? e.getEnd() : e.getStart();

            if (parentEdge != null && e.equals(parentEdge)) {
                continue; // 跳过父边（避免回退）
            }

            if (!visited.getOrDefault(v, false)) {
                dfsForBridges(v, e, disc, low, visited, time, bridges);
                low.put(u, Math.min(low.get(u), low.get(v)));

                // 检查是否为桥
                if (low.get(v) > disc.get(u)) {
                    bridges.add(e);
                }
            } else {
                // back edge
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }
    }
}
