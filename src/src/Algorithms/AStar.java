package Algorithms;

import DataStructures.ArrayList;
import DataStructures.Map;
import DataStructures.MinHeap;
import DataStructures.Set;
import EntryPoints.Main;
import util.*;

import java.util.Comparator;

public class AStar implements Algorithm {
    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
        ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> orderedTargets = orderTargets(startVertex, targets);
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

        return new AlgorithmResults(0, combinedPath);
    }


    private ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> orderTargets(Vertex startVertex, ArrayList<Vertex> targets) {
        ArrayList<Vertex> targetsCopy = new ArrayList<>(targets);

        ArrayList<Pair<Vertex, Integer>> targetVerticesOrder = new ArrayList<>();

        Pair<Vertex, Integer> minDistance = null;
        Vertex currVertex = startVertex;
        while (!targetsCopy.isEmpty()) {
            for (Vertex v : targetsCopy) {
                int distance = heuristic(currVertex, v);

                if (minDistance == null) {
                    minDistance = new Pair<>(v, distance);
                } else {
                    if (minDistance.getSnd() > distance) {
                        minDistance = new Pair<>(v, distance);
                    }
                }
            }

            targetVerticesOrder.add(minDistance);
            targetsCopy.remove(minDistance.getFst());
            currVertex = minDistance.getFst();
            minDistance = null;
        }

        ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> targetPaths = new ArrayList<>();

        targetPaths.add(new Pair<>(new Pair<>(startVertex, targetVerticesOrder.getFirst().getFst()), targetVerticesOrder.getFirst().getSnd()));
        for (int i = 0; i < targetVerticesOrder.size() - 1; i++) {
            targetPaths.add(new Pair<>(new Pair<>(targetVerticesOrder.get(i).getFst(), targetVerticesOrder.get(i+1).getFst()), targetVerticesOrder.get(i+1).getSnd()));
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

        return combined;
    }

    @Override
    public String getName() {
        return "A*";
    }
}
