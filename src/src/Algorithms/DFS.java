package Algorithms;

import DataStructures.Map;
import DataStructures.Set;
import EntryPoints.Main;
import util.*;

public class DFS {
    private static int estimatedMaxPathWeight;
    private static Path minPath;
    private static int visitedNodes;

    public static Pair<Integer, Path> findPath(Vertex startVertex, int numTargets) {
        estimatedMaxPathWeight = Integer.MAX_VALUE;
        minPath = null;
        visitedNodes = 0;

        dfs(1, numTargets, startVertex, new Set<>(), new Path(1));

        return new Pair<>(visitedNodes, minPath);
    }

    private static void dfs(int currDepth, int numTargets, Vertex curr, Set<Vertex> visited, Path path) {
        visitedNodes++;

        if (currDepth > estimatedMaxPathWeight) return;

        if (!visited.tryAdd(curr)) return;

        //Update path
        path.add(curr);

        if (curr.c == '1') {
            numTargets--;

            if (numTargets == 0) {
                tryUpdateMinPath(path);

                //Backtrack
                visited.removeLast();
                path.removeLast();
                return;
            }
        }

        int oldWeight = path.getWeight();
        Set<WeightedEdge<Vertex, Vertex>> adjacentVertices = Main.weightedGraph.verticesAdjacentTo(curr);

        //Continue dfs
        for (WeightedEdge<Vertex,Vertex> edge : adjacentVertices) {
            //Get opposite vertex
            Vertex otherVertex = (edge.getFst() == curr) ? edge.getSnd() : edge.getFst();

            //Adjust weight
            path.setWeight(oldWeight + edge.getWeight());
            dfs(currDepth + edge.getWeight(), numTargets, otherVertex, visited, path);
        }

        //Backtrack
        visited.removeLast();
        path.removeLast();
    }

    private static void tryUpdateMinPath(Path newPath) {
        if (minPath == null) {
            minPath = new Path(newPath);
            estimatedMaxPathWeight = newPath.getWeight();
        } else {
            if (newPath.getWeight() < minPath.getWeight()) {
                minPath = new Path(newPath);
                estimatedMaxPathWeight = newPath.getWeight();
            }
        }
    }
}