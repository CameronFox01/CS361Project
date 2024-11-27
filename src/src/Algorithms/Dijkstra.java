package Algorithms;

import DataStructures.*;
import util.*;

import java.util.Arrays;

//public class Dijkstra {
//    private static Path minPath;
//    public static Path start(WeightedGraph<Vertex> graph, int startingVertex){
//        int v = graph.size();
//        int[] dist = new int[v];
//        Arrays.fill(dist, Integer.MAX_VALUE);
//        boolean[] visited = new boolean[v];
//
//        PriorityQueue<Node> pq = new PriorityQueue<>();
//        pq.offer(new Node(startingVertex, 0));
//
//        while(!pq.isEmpty()){
//            Node current = pq.poll();
//            Vertex u = current.node;
//
//           int uIndex = graph.g
//        }
//
////        for(Vertex vertex : graph.getVertices()){
////            System.out.println(vertex);
////        }
////
////        for(Edge edge : graph.getEdges()){
////            System.out.println(edge);
////        }
//
//        //graph.displayGraph();
//        return minPath;
//    }
//}
class Node implements  Comparable<Node>{
    Vertex node;
    int cost;
    Node(Vertex node, int cost){
        this.node = node;
        this.cost = cost;
    }
    @Override
    public int compareTo(Node other){
        return Integer.compare(this.cost, other.cost);
    }
}
