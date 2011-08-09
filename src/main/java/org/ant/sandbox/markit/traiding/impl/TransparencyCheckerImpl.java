package org.ant.sandbox.markit.traiding.impl;

import org.ant.sandbox.markit.traiding.Exchange;
import org.ant.sandbox.markit.traiding.TransparencyChecker;

import java.util.*;

/**
 * @author Anton Tychyna
 */
public class TransparencyCheckerImpl implements TransparencyChecker {
    private Graph graph = new Graph();
    private Set<String> visited = new HashSet<String>();

    public boolean isTransparent(Set<Exchange> exchanges) {
        // build the graph
        buildGraph(exchanges);
        visited.clear();
        // inspired by Kosaraju's algorithm for strongly connected components, only in our case there should be exactly
        // one SCC. therefore we don't need to maintain stack and can simply run 2 depth-first searches - on original and reversed graph
        String firstVertex = graph.getVertexes().iterator().next();
        int vertexesCount = graph.getVertexes().size();
        dfs(firstVertex);
        if (visited.size() != vertexesCount) {
            return false;
        }
        graph.reverseGraph();
        visited.clear();
        dfs(firstVertex);
        return visited.size() == vertexesCount;
    }

    private void buildGraph(Set<Exchange> exchanges) {
        graph.clear();
        for (Exchange exch : exchanges) {
            for (String con : exch.getOutgoingConnections()) {
                graph.addEdge(exch.getName(), con);
            }
        }
    }

    private void dfs(String vertex) {
        visited.add(vertex);
        for (String v : graph.getConnectedVertexes(vertex)) {
            if (!visited.contains(v)) {
                dfs(v);
            }
        }
    }

    private static final class Graph {
        private Map<String, Set<String>> edges = new HashMap<String, Set<String>>();
        private Set<String> vertexes = new HashSet<String>();

        private void reverseGraph() {
            Map<String, Set<String>> reversedGraph = new HashMap<String, Set<String>>();
            for (String from : vertexes) {
                for (String to : edges.get(from)) {
                    if (!reversedGraph.containsKey(to)) {
                        reversedGraph.put(to, new HashSet<String>());
                    }
                    reversedGraph.get(to).add(from);
                }
            }
            edges = reversedGraph;
        }

        public Set<String> getVertexes() {
            return vertexes;
        }

        public void clear() {
            edges.clear();
            vertexes.clear();
        }

        public void addEdge(String from, String to) {
            if (!edges.containsKey(from)) {
                edges.put(from, new HashSet<String>());
            }
            vertexes.add(from);
            vertexes.add(to);
            edges.get(from).add(to);
        }

        public Set<String> getConnectedVertexes(String from) {
            return edges.containsKey(from) ? edges.get(from) : Collections.<String>emptySet();
        }
    }
}
