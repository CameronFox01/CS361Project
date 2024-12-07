package Algorithms;

import DataStructures.ArrayList;
import DataStructures.List;
import DataStructures.Map;
import DataStructures.Queue;
import EntryPoints.Main;
import util.AlgorithmResults;
import util.Path;
import util.Vertex;
import util.WeightedEdge;

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
        //The issue is in the for loop
        int vertexCount = Main.weightedGraph.getVertices().size();
        for (int i = 1; i < vertexCount; i++) {
            for (WeightedEdge<Vertex, Vertex> edge : Main.weightedGraph.getEdges()) {
                Vertex u = edge.getFst();
                Vertex v = edge.getSnd();
                int weight = edge.getWeight();


                if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    predecessors.put(v, u);
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

    @Override
    public String getName() {
        return "Bellman-Ford";
    }
}
