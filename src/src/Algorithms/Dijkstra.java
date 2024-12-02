package Algorithms;

import DataStructures.*;
import EntryPoints.Main;
import util.*;

import java.util.Comparator;

public class Dijkstra implements Algorithm {

    @Override
    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
        int verticesVisited = 0;
        ArrayList<Path> uncombinedPaths = new ArrayList<>();

        for (Vertex target : targets) {
            Set<Vertex> openSetVertices = new Set<>();
            MinHeap<Pair<Vertex, Integer>> openSet = new MinHeap<>(Comparator.comparingInt(Pair::getSnd));
            openSet.add(new Pair<>(startVertex, 0));
            openSetVertices.add(startVertex);

            Map<Vertex, Integer> gScores = new Map<>();
            Map<Vertex, Vertex> cameFrom = new Map<>();

            for (Vertex vertex : Main.weightedGraph.getVertices()) {
                gScores.put(vertex, Integer.MAX_VALUE);
            }
            gScores.put(startVertex, 0);

            while (!openSet.isEmpty()) {
                Pair<Vertex, Integer> current = openSet.poll();

                verticesVisited++;

                if (current.getFst() == target) {
                    uncombinedPaths.add(reconstructPath(cameFrom, current.getFst(), startVertex));
                    break;
                }

                for (WeightedEdge<Vertex, Vertex> edge : Main.weightedGraph.verticesAdjacentTo(current.getFst())) {
                    Vertex neighbor = (edge.getFst() == current.getFst()) ? edge.getSnd() : edge.getFst();

                    int tentativeGScore = gScores.get(current.getFst()) + edge.getWeight();

                    if (tentativeGScore < gScores.get(neighbor)) {
                        cameFrom.put(neighbor, current.getFst());
                        gScores.put(neighbor, tentativeGScore);

                        if (!openSetVertices.contains(neighbor)) {
                            openSet.add(new Pair<>(neighbor, gScores.get(neighbor)));
                            openSetVertices.add(neighbor);
                        }
                    }
                }
            }

            if (uncombinedPaths.isEmpty()) {
                return new AlgorithmResults(0, new Path());
            }
        }

        Path combinedPath = combinePaths(uncombinedPaths);
        combinedPath.setFullPath();

        return new AlgorithmResults(verticesVisited, combinedPath);
    }

    private Path reconstructPath(Map<Vertex, Vertex> cameFrom, Vertex current, Vertex startVertex) {
        Path path = new Path();
        path.add(current);

        while (mapContains(cameFrom, current)) {
            Vertex previousVertex = cameFrom.get(current);
            path.getPath().add(previousVertex, 0);
            current = previousVertex;

            if (current == startVertex) break;
        }

        return path;
    }

    private boolean mapContains(Map<Vertex, Vertex> map, Vertex vertex) {
        return map.keys().contains(vertex);
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
        return "Dijkstra";
    }
}

