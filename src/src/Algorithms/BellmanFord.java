package Algorithms;

import DataStructures.ArrayList;
import DataStructures.List;
import EntryPoints.Main;
import util.AlgorithmResults;
import util.Path;
import util.Vertex;
import util.WeightedEdge;

//This algorithm sucks for Signle Shortest Path Algorithm compared to Dijkstra
//because its time complexity is O(VE) while Dijkstra is O((E+V) * log(v))
//But this algorithm is usefull when having an algorithm that can handle negative
// edge weights. Dijkstra will inifit loop while Bellman Ford while not loop forever


public class BellmanFord implements Algorithm{
    @Override
    public AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets) {
        int verticesVisited = 0;
        ArrayList<Path> uncombinedPaths = new ArrayList<>();


        Path combinedPath = combinePaths(uncombinedPaths);
        combinedPath.setFullPath();
        return null;
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
        return "BellmanFord";
    }
}
