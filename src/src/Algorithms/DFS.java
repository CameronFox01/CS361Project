package Algorithms;

import DataStructures.Set;
import EntryPoints.Main;
import util.*;

public class DFS {
    private static int estimatedMaxPathWeight;
    private static Path minPath;

    public static Path findPath(Vertex startVertex, int numTargets) {
        estimatedMaxPathWeight = Integer.MAX_VALUE;
        minPath = null;

        dfs(1, numTargets, startVertex, new Set<>(), new Path(1));

        Path pathFound = minPath;
        minPath = null;

        return pathFound;
    }

    private static void dfs(int currDepth, int numTargets, Vertex curr, Set<Vertex> visited, Path path) {
        if (currDepth > estimatedMaxPathWeight) return;

        if (!visited.tryAdd(curr)) return;

        //Update path
        path.add(curr);

        if (curr.c == '1') {
            numTargets--;

            if (numTargets == 0) {
                tryUpdateMinPath(path);

                //Backtrack
                visited.remove(curr);
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
        visited.remove(curr);
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