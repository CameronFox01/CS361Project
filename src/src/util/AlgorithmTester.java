package util;

import Algorithms.Algorithm;
import DataStructures.ArrayList;
import EntryPoints.Main;

public class AlgorithmTester {
    private final Algorithm algorithm;
    private final int numTargets;

    public AlgorithmTester(Algorithm algorithm, int numTargets) {
        this.algorithm = algorithm;
        this.numTargets = numTargets;
    }

    public void testFunction(int numTrials, int numSamples, Vertex startVertex) {
        //Initial run
        AlgorithmResults results = algorithm.runAlgorithm(startVertex, numTargets);

        //Check if path found
        if (!results.pathFound().isFullPath()) {
            System.out.println("Path not found by " + algorithm.getName());
            return;
        }

        System.out.println("Path found by " + algorithm.getName() + ":");

        results.pathFound().displayPath(Main.fileArray);

        //Do n runs for t trials
        ArrayList<Long> runtimes = new ArrayList<>();
        ArrayList<Long> meanRuntimes = new ArrayList<>();

        for (int i = 0; i < numTrials; i++) {
            for (int j = 0; j < numSamples; j++) {
                long startTime = System.nanoTime();
                algorithm.runAlgorithm(startVertex, numTargets);
                long endTime = System.nanoTime();

                runtimes.add((endTime - startTime));
            }

            long meanTime = nanoToMs(meanOfRuntimes(runtimes));

            System.out.println("Mean time of trial #" + (i+1) + ": " + meanTime + "ms");
            meanRuntimes.add(meanTime);
            runtimes.clear();
        }

        //Final Results
        System.out.println("For " + numTrials + " trials with " + numSamples + " samples each, Mean runtime: " + meanOfRuntimes(meanRuntimes) + "ms");
        System.out.println("Memory used: " + results.memoryUsed());
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
