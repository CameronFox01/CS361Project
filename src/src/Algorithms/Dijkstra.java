package Algorithms;

import DataStructures.Graph;
import DataStructures.WeightedGraph;
import EntryPoints.Main;
import util.AlgorithmResults;
import util.Edge;
import util.Vertex;
import util.WeightedEdge;

import java.util.Arrays;

public class Dijkstra implements Algorithm {
    public AlgorithmResults runAlgorithm(Vertex startingVertex, int numTargets){
        int startingX = startingVertex.x;
        int startingY = startingVertex.y;
        //Creating the array
        double[] visitedArray = new double[Main.weightedGraph.getVertices().size()];
        //Setting the array to positive infinity
        Arrays.fill(visitedArray, Double.POSITIVE_INFINITY);
        int index = 0;
        for(Vertex vertex : Main.weightedGraph.getVertices()){
            if(startingX == vertex.x && startingY == vertex.y){
                visitedArray[index] = 0.0;
                break;
            }
            index++;
        }

//        for(Vertex vertex : graph.getVertices()){
//            System.out.println(vertex);
//        }
//
//        for(Edge edge : graph.getEdges()){
//            System.out.println(edge);
//        }

        //graph.displayGraph();

        return null;
    }

    @Override
    public String getName() {
        return "Dijkstra";
    }
}
