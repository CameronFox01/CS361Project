package Algorithms;

import DataStructures.ArrayList;
import util.AlgorithmResults;
import util.Vertex;

public interface Algorithm {
    AlgorithmResults runAlgorithm(Vertex startVertex, ArrayList<Vertex> targets);
    String getName();
}
