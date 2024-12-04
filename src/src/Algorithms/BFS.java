package Algorithms;

import DataStructures.ArrayList;
import DataStructures.Map;
import DataStructures.Queue;
import DataStructures.Set;
import EntryPoints.Main;
import util.*;

public class BFS implements Algorithm {

    @Override
    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
        int verticesVisited = 0;
        ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> orderedTargets = orderTargetsByDistance(startVertex, targets);
        ArrayList<Path> uncombinedPaths = new ArrayList<>();

        for (Pair<Pair<Vertex, Vertex>, Integer> targetPair : orderedTargets) {
            Vertex currStartVertex = targetPair.getFst().getFst();
            Vertex target = targetPair.getFst().getSnd();

            // Initialize data structures
            Set<Vertex> visited = new Set<>();
            Queue<Pair<Vertex, Vertex>> queue = new Queue<>(); // Pair<vertex, parent>
            Map<Vertex, Vertex> parentMap = new Map<>();

            // Initialize the BFS
            queue.offer(new Pair<>(currStartVertex, currStartVertex));
            visited.add(currStartVertex);

            // Start BFS loop
            while (!queue.isEmpty()) {
                verticesVisited++;

                Pair<Vertex, Vertex> currentPair = queue.poll(); // Dequeue the front of the queue
                Vertex current = currentPair.getFst();
                Vertex parent = currentPair.getSnd();

                if (current == target) {
                    // Reconstruct path to the target
                    parentMap.put(current, parent);
                    uncombinedPaths.add(reconstructPath(parentMap, current, currStartVertex));
                    break;
                }

                // Explore all adjacent vertices (neighbors)
                for (WeightedEdge<Vertex, Vertex> edge : Main.weightedGraph.verticesAdjacentTo(current)) {
                    Vertex neighbor = (edge.getFst() == current) ? edge.getSnd() : edge.getFst();

                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(new Pair<>(neighbor, current)); // Enqueue the neighbor (Pair<>(neighbor, parent)
                        parentMap.put(neighbor, current); // Track the parent
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

    private ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> orderTargetsByDistance(Vertex startVertex, ArrayList<Vertex> targets) {
        //Create a copy of the targets to avoid modifying the original list
        ArrayList<Vertex> targetsCopy = new ArrayList<>(targets);
        ArrayList<Pair<Vertex, Integer>> orderedTargets = new ArrayList<>();
        Vertex currentVertex = startVertex;

        //Find distance for each target from start vertex
        while (!targetsCopy.isEmpty()) {
            Pair<Vertex, Integer> closestTarget = null;

            for (Vertex target : targetsCopy) {
                //Get manhattan distance
                int distance = manhattanDistance(target, currentVertex);

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

    private int manhattanDistance(Vertex target, Vertex currentVertex) {
        return (Math.abs((target.x - currentVertex.x)) + Math.abs((target.y - currentVertex.y)));
    }

    private Path reconstructPath(Map<Vertex, Vertex> cameFrom, Vertex current, Vertex startVertex) {
        Path path = new Path();
        path.add(current);

        while (mapContains(cameFrom, current)) {
            Vertex otherVertex = getOtherVertexInMap(cameFrom, current);
            path.getPath().add(otherVertex, 0);
            current = otherVertex;

            if (current == startVertex) break;
        }

        return path;
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

    private Path combinePaths(ArrayList<Path> paths) {
        Path combined = new Path();
        combined.add(paths.getFirst().getPath().getFirst()); // Add the first vertex of the first path

        for (Path path : paths) {
            path.getPath().removeFirst(); // Remove the first vertex (already added)

            while (!path.getPath().isEmpty()) {
                combined.add(path.getPath().removeFirst()); // Add the rest of the vertices
            }
        }
        return combined;
    }

    @Override
    public String getName() {
        return "BFS";
    }
}
