package Algorithms;

import DataStructures.ArrayList;
import DataStructures.List;
import DataStructures.Map;
import DataStructures.Queue;
import EntryPoints.Main;
import util.*;

import java.lang.annotation.Target;

//This algorithm sucks for Single Shortest Path Algorithm compared to Dijkstra
//because its time complexity is O(VE) while Dijkstra is O((E+V) * log(v))
//But this algorithm is useful when having an algorithm that can handle negative
// edge weights. Dijkstra will infinite loop while Bellman Ford while not loop forever

public class BellmanFord implements Algorithm {

    @Override
    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
        int verticesVisited = 0;
        ArrayList<Path> uncombinedPaths = new ArrayList<>();

        // Iterate for each target vertex
        //for (Vertex target : targets) {
        // Initialize distance map and predecessor map
        Map<Vertex, Integer> distances = new Map<>();
        Map<Vertex, Vertex> predecessors = new Map<>();

        // Set initial distances to infinity and start vertex to 0
        for (Vertex vertex : Main.weightedGraph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }

        distances.put(startVertex, 0);

        // Relax all edges V-1 times
        int vertexCount = Main.weightedGraph.getVertices().size();

        for (int i = 1; i < vertexCount; i++) {
            for (Vertex vertex : Main.weightedGraph.getVertices()) {
                for (WeightedEdge<Vertex, Vertex> edge : Main.weightedGraph.verticesAdjacentTo(vertex)) {
                    Vertex u = edge.getFst();
                    Vertex v = edge.getSnd();
                    int weight = edge.getWeight();

                    if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
                        distances.put(v, distances.get(u) + weight);
                        predecessors.put(v, u);
                    }
                }
            }
        }

        // Check for negative-weight cycles
//            for(int i = 0; i < Main.weightedGraph.getVertices().size(); i++) {
//                for (WeightedEdge<Vertex, Vertex> edge : Main.weightedGraph.getEdges()) {
//                    Vertex u = edge.getFst();
//                    Vertex v = edge.getSnd();
//                    int weight = edge.getWeight();
//
//                    if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
//                        System.out.println("Graph contains a negative weight cycle");
//                        return new AlgorithmResults(0, new Path());  // Handle error appropriately
//                    }
//                }
//            }

        // Reconstruct the shortest path to the target
        for (Vertex target : targets) {
            if (distances.get(target) < Integer.MAX_VALUE) {
                Path path = reconstructPath(predecessors, target, startVertex);
                uncombinedPaths.add(path);
                verticesVisited += path.getPath().size();  // Count visited vertices in the path
            }
        }
        //}

        // If no paths were found, return an empty path
        if (uncombinedPaths.isEmpty()) {
            return new AlgorithmResults(0, new Path());
        }
        // Combine all paths into one if needed
        Path combinedPath = combinePaths(uncombinedPaths);
        combinedPath.setFullPath();

        return new AlgorithmResults(verticesVisited, combinedPath);
    }

    private Path reconstructPath(Map<Vertex, Vertex> predecessors, Vertex target, Vertex startVertex) {
        Path path = new Path();
        path.add(target);

        // Trace the path back to the start vertex using the predecessors map
        while (predecessors.containsKey(target)) {
            target = predecessors.get(target);
            path.add(target);
        }

        // Reverse the path to get start-to-target order
        path.reverse();
        return path;
    }

    private Path combinePaths(ArrayList<Path> paths) {
        Path combined = new Path();

        combined.add(paths.get(0).getPath().getFirst());

        for (Path path : paths) {
            path.getPath().removeFirst();
            while (!path.getPath().isEmpty()) {
                combined.add(path.getPath().removeFirst());
            }
        }

        combined.setWeight(calculatePathDistance(combined));
        return combined;
    }

    private int calculatePathDistance(Path path) {
        int distance = 0;

        List<WeightedEdge<Vertex, Vertex>> edges = Main.weightedGraph.getEdges();

        for (int i = 0; i < path.getPath().size() - 1; i++) {
            for (WeightedEdge<Vertex, Vertex> edge : edges) {
                if (edge.getFst().equals(path.getPath().get(i)) && edge.getSnd().equals(path.getPath().get(i + 1))) {
                    distance += edge.getWeight();
                    break;
                }
            }
        }

        return distance;
    }

    private ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> orderTargetsByDistance(Vertex startVertex, ArrayList<Vertex> targets) {
        // Create a copy of the targets to avoid modifying the original list
        ArrayList<Vertex> targetsCopy = new ArrayList<>(targets);
        ArrayList<Pair<Vertex, Integer>> orderedTargets = new ArrayList<>();
        Vertex currentVertex = startVertex;

        //Find distance for each target from start vertex
        while (!targetsCopy.isEmpty()) {
            Pair<Vertex, Integer> closestTarget = null;

            for (Vertex target : targetsCopy) {
                int distance = heuristic(currentVertex, target);

                if (closestTarget == null || distance < closestTarget.getSnd()) {
                    closestTarget = new Pair<>(target, distance);
                }
            }


            orderedTargets.add(closestTarget);
            targetsCopy.remove(closestTarget.getFst());
            currentVertex = closestTarget.getFst();
        }


        ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> targetPaths = new ArrayList<>();

        //Add the path from the starting vertex to the first target
        if (!orderedTargets.isEmpty()) {
            targetPaths.add(new Pair<>(new Pair<>(startVertex, orderedTargets.get(0).getFst()), orderedTargets.get(0).getSnd()));
        }

        //Add paths between consecutive targets
        for (int i = 0; i < orderedTargets.size() - 1; i++) {
            Vertex from = orderedTargets.get(i).getFst();
            Vertex to = orderedTargets.get(i + 1).getFst();
            int distance = orderedTargets.get(i + 1).getSnd();
            targetPaths.add(new Pair<>(new Pair<>(from, to), distance));
        }

        return targetPaths;
    }

    private boolean mapContains(Map<Vertex, Vertex> map, Vertex vertex) {
        return map.keys().contains(vertex) || map.values().contains(vertex);
    }

    private Vertex getOtherVertexInMap(Map<Vertex, Vertex> map, Vertex vertex) {
        for (Pair<Vertex, Vertex> pair : map.entrySet()) {
            if (pair.getSnd().equals(vertex)) {
                map.remove(pair.getFst());
                return pair.getFst();
            } else if (pair.getFst().equals(vertex)) {
                map.remove(pair.getFst());
                return pair.getSnd();
            }
        }

        return null;
    }

    private int heuristic(Vertex v1, Vertex v2) {
        return (Math.abs((v2.x - v1.x)) + Math.abs((v2.y - v1.y)));
    }

    @Override
    public String getName() {
        return "Bellman-Ford";
    }
}
