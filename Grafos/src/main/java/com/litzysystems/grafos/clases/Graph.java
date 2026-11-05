package com.litzysystems.grafos.clases;

import java.util.*;

public class Graph {

    private final Map<String, List<String>> graph =
            new LinkedHashMap<>();

    public boolean addVertex(String vertex) {
        if (vertex == null || vertex.trim().isEmpty()
                || graph.containsKey(vertex.trim())) {
            return false;
        }

        graph.put(vertex.trim(), new ArrayList<>());
        return true;
    }

    public boolean addEdge(String a, String b) {
        if (a == null || b == null) return false;

        a = a.trim();
        b = b.trim();

        if (!graph.containsKey(a) || !graph.containsKey(b)
                || a.equals(b) || graph.get(a).contains(b)) {
            return false;
        }

        graph.get(a).add(b);
        graph.get(b).add(a);

        return true;
    }

    public List<String> bfs(String start) {
        List<String> result = new ArrayList<>();

        if (!graph.containsKey(start)) return result;

        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);

            for (String neighbor : graph.get(current)) {
                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    public List<String> dfs(String start) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new LinkedHashSet<>();

        if (graph.containsKey(start)) {
            dfsRecursive(start, visited, result);
        }

        return result;
    }

    private void dfsRecursive(
            String vertex,
            Set<String> visited,
            List<String> result) {

        visited.add(vertex);
        result.add(vertex);

        for (String neighbor : graph.get(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    public Map<String, List<String>> getGraph() {
        return graph;
    }

    public void clear() {
        graph.clear();
    }

    public boolean isEmpty() {
        return graph.isEmpty();
    }
}