//package Algorithms;
//
//import DataStructures.ArrayList;
//import DataStructures.Set;
//import EntryPoints.Main;
//import util.*;
//
//public class BFS implements Algorithm {
//
//    @Override
//    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
//        int verticesVisited = 0;
//        ArrayList<Path> uncombinedPaths = new ArrayList<>();
//
//        for (Vertex target : targets) {
//            Set<Vertex> visited = new Set<>();
//            ArrayList<Vertex> queue = new ArrayList<>();
//            ArrayList<Vertex> parentMap = new ArrayList<>();
//
//            queue.add(startVertex);
//            visited.add(startVertex);
//            parentMap.add(null); // Start vertex has no parent
//
//            while (!queue.isEmpty()) {
//                Vertex current = queue.remove(0); // Dequeue the front of the queue
//                verticesVisited++;
//
//                if (current == target) {
//                    uncombinedPaths.add(reconstructPath(parentMap, current, startVertex));
//                    break;
//                }
//
//                // Explore all adjacent vertices (neighbors)
//                for (WeightedEdge<Vertex, Vertex> edge : Main.weightedGraph.verticesAdjacentTo(current)) {
//                    Vertex neighbor = (edge.getFst() == current) ? edge.getSnd() : edge.getFst();
//
//                    if (!visited.contains(neighbor)) {
//                        visited.add(neighbor);
//                        queue.add(neighbor); // Enqueue the neighbor
//                        parentMap.add(current); // Track the parent
//                    }
//                }
//            }
//
//            if (uncombinedPaths.isEmpty()) {
//                return new AlgorithmResults(verticesVisited, new Path());
//            }
//        }
//
//        Path combinedPath = combinePaths(uncombinedPaths);
//        combinedPath.setFullPath();
//
//        return new AlgorithmResults(verticesVisited, combinedPath);
//    }
//
//    private Path reconstructPath(ArrayList<Vertex> parentMap, Vertex current, Vertex startVertex) {
//        Path path = new Path();
//        path.add(current);
//
//        // Backtrack from the target to the start using parentMap
//        while (current != startVertex) {
//            int parentIndex = parentMap.indexOf(current);
//            if (parentIndex == -1) break; // Safety check, although it shouldn't happen in BFS
//            current = parentMap.get(parentIndex);
//            path.getPath().add(current, 0); // Add to the front of the path (reverse order)
//        }
//
//        path.reverse(); // Reverse to get the correct order from start to target
//        return path;
//    }
//
//    private Path combinePaths(ArrayList<Path> paths) {
//        Path combined = new Path();
//        int combinedWeight = 0;
//
//        combined.add(paths.get(0).getPath().get(0)); // Add the first vertex of the first path
//
//        for (Path path : paths) {
//            path.getPath().remove(0); // Remove the first vertex (already added)
//
//            while (!path.getPath().isEmpty()) {
//                combined.add(path.getPath().remove(0)); // Add the rest of the vertices
//            }
//        }
//
//        combined.setWeight(combinedWeight);
//        return combined;
//    }
//
//    @Override
//    public String getName() {
//        return "BFS";
//    }
//}

package Algorithms;

import DataStructures.ArrayList;
import DataStructures.Set;
import EntryPoints.Main;
import util.*;

public class BFS implements Algorithm {

    @Override
    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
        int verticesVisited = 0;
        ArrayList<Path> uncombinedPaths = new ArrayList<>();

        for (Vertex target : targets) {
            Set<Vertex> visited = new Set<>();
            ArrayList<Vertex> queue = new ArrayList<>();
            ArrayList<Vertex> parentMap = new ArrayList<>();

            // Initialize the BFS
            queue.add(startVertex);
            visited.add(startVertex);
            parentMap.add(null); // Start vertex has no parent

            // Start BFS loop
            while (!queue.isEmpty()) {
                Vertex current = queue.remove(0); // Dequeue the front of the queue
                verticesVisited++;

                if (current == target) {
                    // Reconstruct path to the target
                    uncombinedPaths.add(reconstructPath(parentMap, current, startVertex));
                    break;
                }

                // Explore all adjacent vertices (neighbors)
                for (WeightedEdge<Vertex, Vertex> edge : Main.weightedGraph.verticesAdjacentTo(current)) {
                    Vertex neighbor = (edge.getFst() == current) ? edge.getSnd() : edge.getFst();

                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor); // Enqueue the neighbor
                        parentMap.add(current); // Track the parent
                    }
                }
            }

            if (uncombinedPaths.isEmpty()) {
                // If no path is found, return an empty path
                return new AlgorithmResults(verticesVisited, new Path());
            }
        }

        // Combine all found paths
        Path combinedPath = combinePaths(uncombinedPaths);
        combinedPath.setFullPath();

        return new AlgorithmResults(verticesVisited, combinedPath);
    }

    private Path reconstructPath(ArrayList<Vertex> parentMap, Vertex current, Vertex startVertex) {
        Path path = new Path();
        path.add(current);

        // Backtrack from the target to the start using parentMap
        while (current != startVertex) {
            int parentIndex = parentMap.indexOf(current);
            if (parentIndex == -1) break; // Safety check, although it shouldn't happen in BFS
            current = parentMap.get(parentIndex);
            path.getPath().add(current, 0); // Add to the front of the path (reverse order)
        }

        path.reverse(); // Reverse to get the correct order from start to target
        return path;
    }

    private Path combinePaths(ArrayList<Path> paths) {
        Path combined = new Path();
        int combinedWeight = 0;

        combined.add(paths.get(0).getPath().get(0)); // Add the first vertex of the first path

        for (Path path : paths) {
            path.getPath().remove(0); // Remove the first vertex (already added)

            while (!path.getPath().isEmpty()) {
                combined.add(path.getPath().remove(0)); // Add the rest of the vertices
            }
        }

        combined.setWeight(combinedWeight);
        return combined;
    }

    @Override
    public String getName() {
        return "BFS";
    }
}
