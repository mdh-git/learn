package com.mdh.datastructure.cycle;

import java.util.*;

/**
 * 图
 */
public class Graph {

    // 邻接表：每个点 -> 相邻点列表
    private Map<Point, List<Point>> adj = new HashMap<>();
    // 边集合：用于快速查找两点之间的 Edge
    private Map<String, Edge> edgeMap = new HashMap<>();


    public void addEdge(Edge edge) {
        Point start = edge.getStart();
        Point end = edge.getEnd();

        adj.computeIfAbsent(start, k -> new ArrayList<>()).add(end);
        adj.computeIfAbsent(end, k -> new ArrayList<>()).add(start);

        // 使用 "minId-maxId" 作为无向边的 key，确保唯一性
        String key = getEdgeKey(start, end);
        edgeMap.put(key, edge);
    }

    private String getEdgeKey(Point start, Point end) {
        String id1 = start.getId(), id2 = end.getId();
        return id1.compareTo(id2) < 0 ? id1 + "-" + id2 : id2 + "-" + id1;
    }

    public Optional<List<Edge>> findCycle() {
        Set<Point> visited = new HashSet<>();
        List<Point> path = new ArrayList<>();

        for (Point vertex : adj.keySet()) {
            if (!visited.contains(vertex)) {
                List<Point> cyclePath = dfs(vertex, visited, null, path);
                if (cyclePath != null) {
                    return Optional.of(pointsToEdges(cyclePath));
                }
            }
        }
        // 无环
        return Optional.empty();
    }

    private List<Point> dfs(Point current, Set<Point> visited, Point parent, List<Point> path) {
        visited.add(current);
        path.add(current);

        for (Point neighbor : adj.getOrDefault(current, Collections.emptyList())) {
            if (!neighbor.equals(parent)) {
                if (visited.contains(neighbor)) {
                    // 找到环！从 path 中提取从 neighbor 到 current 的部分
                    int idx = path.indexOf(neighbor);
                    if (idx != -1) {
                        List<Point> cyclePoints = new ArrayList<>(path.subList(idx, path.size()));
                        cyclePoints.add(neighbor); // 闭合环（可选，便于理解）
                        return cyclePoints;
                    }
                } else {
                    List<Point> result = dfs(neighbor, visited, current, path);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        // 回溯
        path.remove(path.size() - 1);
        return null;
    }

    private List<Edge> pointsToEdges(List<Point> cyclePoints) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < cyclePoints.size() - 1; i++) {
            Point a = cyclePoints.get(i);
            Point b = cyclePoints.get(i + 1);
            String key = getEdgeKey(a, b);
            Edge edge = edgeMap.get(key);
            if (edge != null) {
                edges.add(edge);
            }
        }
        return edges;
    }

}
