package util;

import Algorithms.Algorithm;
import DataStructures.ArrayList;
import EntryPoints.Main;

public class AlgorithmTester {
    private final Algorithm algorithm;
    private final ArrayList<Vertex> targets;

    public AlgorithmTester(Algorithm algorithm, ArrayList<Vertex> targets) {
        this.algorithm = algorithm;
        this.targets = targets;
    }

    public void testFunction(int numTrials, int numSamples, Vertex startVertex, boolean displayPath) {
        System.out.println("Starting test for " + algorithm.getName() + " with " + numTrials + " num trials, and " + numSamples + " num samples.");

        //Initial run
        AlgorithmResults results = algorithm.runAlgorithm(startVertex, targets);

        //Check if path found
        if (!results.pathFound().isFullPath()) {
            System.out.println("Path not found by " + algorithm.getName());
            return;
        }

        System.out.println(algorithm.getName() + " found path");

        if (displayPath) {
            System.out.println("Path found by " + algorithm.getName() + ":");
            results.pathFound().displayPath(Main.fileArray);
        }

        System.out.println("Vertices visited: " + results.verticesVisited());

        //Do n runs for t trials
        ArrayList<Long> runtimes = new ArrayList<>();
        ArrayList<Long> meanRuntimesMS = new ArrayList<>();
        ArrayList<Long> meanRuntimesNS = new ArrayList<>();

        for (int i = 0; i < numTrials; i++) {
            for (int j = 0; j < numSamples; j++) {
                long startTime = System.nanoTime();
                algorithm.runAlgorithm(startVertex, targets);
                long endTime = System.nanoTime();

                runtimes.add((endTime - startTime));
            }

            long meanTimeInNS = meanOfRuntimes(runtimes);
            long meanTimeInMS = nanoToMs(meanTimeInNS);

            System.out.println("Mean time of trial #" + (i+1) + ": " + meanTimeInMS + "ms, " + meanTimeInNS + "ns");
            meanRuntimesMS.add(meanTimeInMS);
            meanRuntimesNS.add(meanTimeInNS);
            runtimes.clear();
        }

        //Final Results
        System.out.println("For " + numTrials + " trial(s) with " + numSamples + " sample(s) each, Mean runtime: " + meanOfRuntimes(meanRuntimesMS) + "ms, " + meanOfRuntimes(meanRuntimesNS) + "ns");
        System.out.println();
    }

    private long meanOfRuntimes(ArrayList<Long> runtimes) {
        long total = 0;

        for (Long runtime : runtimes) {
            total += runtime;
        }

        return total / runtimes.size();
    }

    private long nanoToMs(long nano) {
        return nano / 1_000_000;
    }
}
