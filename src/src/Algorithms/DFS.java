package Algorithms;

import DataStructures.ArrayList;
import DataStructures.MinHeap;
import DataStructures.Set;
import EntryPoints.Main;
import util.*;

import java.util.Comparator;

public class DFS implements Algorithm {
    private static int estimatedMaxPathWeight;
    private static Path minPath;
    private static int visitedNodes;

    @Override
    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
        ArrayList<Pair<Pair<Vertex, Vertex>, Integer>> orderedTargets = orderTargetsByDistance(startVertex, targets);
        ArrayList<Path> uncombinedPaths = new ArrayList<>();
        int totalNodesVisited = 0;

        for (Pair<Pair<Vertex, Vertex>, Integer> targetPair : orderedTargets) {
            estimatedMaxPathWeight = Integer.MAX_VALUE;
            minPath = null;
            visitedNodes = 0;

            //Run dfs for this target pair
            dfs(1, targetPair.getFst().getSnd(), targetPair.getFst().getFst(), new Set<>(), new Path());

            //Ensure a path was found
            if (minPath == null || !minPath.isFullPath()) return new AlgorithmResults(totalNodesVisited, new Path());

            //Add to uncombined paths
            uncombinedPaths.add(minPath);
            totalNodesVisited += visitedNodes;
        }

        return new AlgorithmResults(totalNodesVisited, combinePaths(uncombinedPaths));
    }

    private void dfs(int currDepth, Vertex target, Vertex curr, Set<Vertex> visited, Path path) {
        visitedNodes++;

        if (currDepth > estimatedMaxPathWeight) return;
        if (!visited.tryAdd(curr)) return;

        //Update path
        path.add(curr);

        if (curr == target) {
            tryUpdateMinPath(path);

            //Backtrack
            visited.removeLast();
            path.removeLast();
            return;
        }

        int oldWeight = path.getWeight();
        Set<WeightedEdge<Vertex, Vertex>> adjacentVertices = Main.weightedGraph.verticesAdjacentTo(curr);

        //Continue dfs
        for (WeightedEdge<Vertex,Vertex> edge : prioritizeEdgesInCorrectDirection(adjacentVertices, curr)) {
            //Get opposite vertex
            Vertex otherVertex = (edge.getFst() == curr) ? edge.getSnd() : edge.getFst();

            //Adjust weight
            path.setWeight(oldWeight + edge.getWeight());
            dfs(currDepth + edge.getWeight(), target, otherVertex, visited, path);
        }

        //Backtrack
        visited.removeLast();
        path.removeLast();
    }

    private void tryUpdateMinPath(Path newPath) {
        if (minPath == null) {
            minPath = new Path(newPath);
            minPath.setFullPath(true);
            estimatedMaxPathWeight = newPath.getWeight();
        } else {
            if (newPath.getWeight() < minPath.getWeight()) {
                minPath = new Path(newPath);
                minPath.setFullPath(true);
                estimatedMaxPathWeight = newPath.getWeight();
            }
        }
    }

    private ArrayList<WeightedEdge<Vertex, Vertex>> prioritizeEdgesInCorrectDirection(Set<WeightedEdge<Vertex, Vertex>> edges, Vertex from) {
        //Heap to sort edges with
        MinHeap<Pair<WeightedEdge<Vertex, Vertex>, Integer>> sortedEdges = new MinHeap<>(Comparator.comparingInt(Pair::getSnd)) ;

        for (WeightedEdge<Vertex, Vertex> edge : edges) {
            //Get other vertex
            Vertex otherVertex = (edge.getFst() == from) ? edge.getSnd() : edge.getFst();

            //Add edge to heap with manhattan distance
            sortedEdges.add(new Pair<>(edge, manhattanDistance(otherVertex, from)));
        }

        //Empty heap into array to return
        ArrayList<WeightedEdge<Vertex, Vertex>> toReturn = new ArrayList<>();

        while (!sortedEdges.isEmpty()) {
            toReturn.add(sortedEdges.poll().getFst());
        }

        return toReturn;
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

    private Path combinePaths(ArrayList<Path> paths) {
        Path combined = new Path();
        int combinedWeight = 0;

        combined.add(paths.getFirst().getPath().getFirst());

        for (Path path : paths) {
            path.getPath().removeFirst();
            combinedWeight += path.getWeight();

            while (!path.getPath().isEmpty()) {
                combined.add(path.getPath().removeFirst());
            }
        }

        combined.setWeight(combinedWeight);
        combined.setFullPath(true);

        return combined;
    }

    @Override
    public String getName() {
        return "DFS";
    }
}