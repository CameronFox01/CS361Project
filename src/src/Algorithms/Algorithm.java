package Algorithms;

import util.AlgorithmResults;
import util.Vertex;

public interface Algorithm {
    AlgorithmResults runAlgorithm(Vertex startVertex, int numTargets);
    String getName();
}
