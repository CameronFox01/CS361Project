package Algorithms;

import DataStructures.*;
import EntryPoints.Main;
import util.*;

import java.util.Comparator;

public class AStar implements Algorithm {
    @Override
    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
        int verticesVisited = 0;

        ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> orderedTargets = orderTargetsByDistance(startVertex, targets);
        ArrayList<Path> uncombinedPaths = new ArrayList<>();

        while (!orderedTargets.isEmpty()) {
            Pair<Pair<Vertex, Vertex>, Integer> currentTargetPair = orderedTargets.removeFirst();

            Vertex currentFrom = currentTargetPair.getFst().getFst();
            Vertex currentTarget = currentTargetPair.getFst().getSnd();

            // MinHeap for open set
            Set<Vertex> openSetVertices = new Set<>();
            MinHeap<Pair<Vertex, Integer>> openSet = new MinHeap<>(Comparator.comparingInt(Pair::getSnd));
            openSet.add(new Pair<>(currentFrom, 0));
            openSetVertices.add(currentFrom);

            Map<Vertex, Integer> gScores = new Map<>();
            Map<Vertex, Integer> fScores = new Map<>();
            Map<Vertex, Vertex> cameFrom = new Map<>();

            // Initialize gScores and fScores
            for (Vertex vertex : Main.weightedGraph.getVertices()) {
                gScores.put(vertex, Integer.MAX_VALUE);
                fScores.put(vertex, Integer.MAX_VALUE);
            }
            gScores.put(currentFrom, 0);
            fScores.put(currentFrom, heuristic(currentFrom, currentTarget));

            while (!openSet.isEmpty()) {
                Pair<Vertex, Integer> current = openSet.poll();

                verticesVisited++;

                if (current.getFst() == currentTarget) {
                    uncombinedPaths.add(reconstructPath(cameFrom, current.getFst(), currentFrom));
                    break;
                }

                for (WeightedEdge<Vertex, Vertex> edge : Main.weightedGraph.verticesAdjacentTo(current.getFst())) {
                    Vertex neighbor = (edge.getFst() == current.getFst()) ? edge.getSnd() : edge.getFst();

                    int tentativeGScore = gScores.get(current.getFst()) + heuristic(current.getFst(), neighbor);

                    if (tentativeGScore < gScores.get(neighbor)) {
                        cameFrom.put(neighbor, current.getFst());
                        gScores.put(neighbor, tentativeGScore);
                        fScores.put(neighbor, gScores.get(neighbor) + heuristic(neighbor, currentTarget));

                        if (!openSetVertices.contains(neighbor)) {
                            openSet.add(new Pair<>(neighbor, fScores.get(neighbor)));
                            openSetVertices.add(neighbor);
                        }
                    }
                }
            }

            if (uncombinedPaths.isEmpty()) return new AlgorithmResults(0, new Path());
        }

        Path combinedPath = combinePaths(uncombinedPaths);
        combinedPath.setFullPath(true);

        return new AlgorithmResults(verticesVisited, combinedPath);
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

    private int heuristic(Vertex v1, Vertex v2) {
        return (Math.abs((v2.x - v1.x)) + Math.abs((v2.y - v1.y)));
    }

    private Path combinePaths(ArrayList<Path> paths) {
        Path combined = new Path();

        combined.add(paths.getFirst().getPath().getFirst());

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
                if (edge.getFst().equals(path.getPath().get(i)) && edge.getSnd().equals(path.getPath().get(i+1))) {
                    distance += edge.getWeight();
                    break;
                }
            }
        }

        return distance;
    }

    @Override
    public String getName() {
        return "A*";
    }
}
