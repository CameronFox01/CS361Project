package Algorithms;

import DataStructures.Set;
import EntryPoints.Main;
import util.*;

public class DFS {
    private static int estimatedMaxPathWeight;

    public static Path findPath(Vertex startVertex, int numTargets) {
        estimatedMaxPathWeight = Integer.MAX_VALUE;
        return dfs(0, numTargets, startVertex, new Set<>(), new Path());
    }

    private static Path dfs(int currDepth, int numTargets, Vertex curr, Set<Vertex> visited, Path path) {
        if (currDepth > estimatedMaxPathWeight) return null;

        if (!visited.tryAdd(curr)) return null;

        //Update path
        path.add(curr);

        if (curr.c == '1') {
            numTargets--;

            if (numTargets == 0) {
                //Save new good path
                Path foundPath = new Path(path);
                foundPath.setFullPath(true);

                //Backtrack
                visited.remove(curr);
                path.removeLast();

                return foundPath;
            }
        }

        Path minPath = null;
        int oldWeight = path.getWeight();

        Set<WeightedEdge<Vertex, Vertex>> adjacentVertices = Main.weightedGraph.verticesAdjacentTo(curr);

        //Continue dfs
        for (WeightedEdge<Vertex,Vertex> edge : adjacentVertices) {
            //Get opposite vertex
            Vertex otherVertex = (edge.getFst() == curr) ? edge.getSnd() : edge.getFst();

            //Adjust weight
            path.setWeight(oldWeight + edge.getWeight());
            Path responsePath = dfs(currDepth + edge.getWeight(), numTargets, otherVertex, visited, path);

            //Don't consider path if it's null or not a found path
            if (responsePath == null || !responsePath.isFullPath()) continue;

            //Track the minimum path found
            if (minPath == null || minPath.getWeight() > responsePath.getWeight()) {
                minPath = responsePath;
                updateEstimate(minPath.getWeight());
            }
        }

        //Backtrack
        visited.remove(curr);
        path.removeLast();

        //Return min found path
        return minPath;
    }

    private static void updateEstimate(int newDepth) {
        if (newDepth < estimatedMaxPathWeight) {
            estimatedMaxPathWeight = newDepth;
        }
    }
}