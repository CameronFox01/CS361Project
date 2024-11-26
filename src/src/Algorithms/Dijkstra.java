package Algorithms;

import DataStructures.Graph;
import DataStructures.WeightedGraph;
import util.Edge;
import util.Vertex;
import util.WeightedEdge;

import java.util.Arrays;

public class Dijkstra {
    public static void start(WeightedGraph<Vertex> graph, int[] startingVertex){
        int startingX = startingVertex[0];
        int startingY = startingVertex[1];
        //Creating the array
        double[] visitedArray = new double[graph.getVertices().size()];
        //Setting the array to positive infinity
        Arrays.fill(visitedArray, Double.POSITIVE_INFINITY);
        int index = 0;
        for(Vertex vertex : graph.getVertices()){
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
    }
}
