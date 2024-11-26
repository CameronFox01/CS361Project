package EntryPoints;

import Algorithms.DFS;
import Algorithms.Dijkstra;
import util.*;

import java.io.File;
import DataStructures.*;

import java.util.Scanner;

/**
 * The File is read in as a command line argument.
 */
public class Main {
    public static Vertex[][] fileArray;

    public static WeightedGraph<Vertex> weightedGraph;
    private static int numTargets = 0;

    public static void main(String[] args) {
        List<String> fileString = new ArrayList<>();
        try {
            File file = new File(System.getProperty("user.dir") + "/TestCases/" + args[0]);
            //File file = new File(args[0]);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()){
                fileString.add(sc.nextLine());
            }
        } catch (Exception e){
            System.out.println(e);
        }

        fileArray = to2DArray(fileString);

        weightedGraph = arrayToWeightedGraph(fileArray);

        //Transpose
        Vertex[][] transposedArr = transpose2DArray(fileArray);

        //Compute vertical edges
        WeightedGraph<Vertex> verGraph = arrayToWeightedGraph(transposedArr);

        //Add vertical edges to graph
        for (WeightedEdge<Vertex, Vertex> edge : verGraph.getEdges()) {
            weightedGraph.addEdge(edge);
        }

        //Find number of targets in graph
        for (Vertex[] vertices : fileArray) {
            for (int j = 0; j < fileArray[0].length; j++) {
                if (vertices[j].c == '1') {
                    numTargets++;
                }
            }
        }

        //This is to start the Dijkstra algorithm
        int[] startingVertex = {0, 0};
        Dijkstra.start(weightedGraph ,startingVertex);

        Pair<Integer, Path> dfsPath = DFS.findPath(fileArray[0][0], numTargets);

        System.out.println("Visited " + dfsPath.getFst() + " nodes.");
        if (dfsPath.getSnd() == null) {
            System.out.println("No path found with DFS");
        } else {
            dfsPath.getSnd().displayPath(fileArray);

            testFunction(3, 5, () -> DFS.findPath(fileArray[0][0], numTargets));
        }
    }

    private static void testFunction(int numTrials, int numSamples, Runnable func) {
        for (int i = 0; i < numTrials; i++) {
            ArrayList<Long> runtimes = measureFunctionTime(numSamples, func);

            System.out.println("Mean Time: " + (meanOfRuntimes(runtimes) / 1_000_000) + "ms");
        }
    }

    private static WeightedGraph<Vertex> arrayToWeightedGraph(Vertex[][] arr) {
        WeightedGraph<Vertex> graph = new WeightedGraph<>();

        // Process each row to connect horizontal segments
        for (int i = 0; i < arr.length; i++) {
            ArrayList<Vertex> collectedCells = new ArrayList<>();

            for (int j = 0; j < arr[0].length; j++) {
                collectedCells.add(arr[i][j]);


                // Handle target objects or branches

                if (arr[i][j].c == '1' || // Target object
                        (i > 0 && (arr[i - 1][j].c == '0' || arr[i - 1][j].c == '1')) || // Branch above
                        (i < arr.length - 1 && (arr[i + 1][j].c == '0' || arr[i + 1][j].c == '1')) || // Branch below
                        arr[i][j].c == 'w' || // wall
                        j == arr[0].length - 1 // End of row
                ) {
                    if (!collectedCells.isEmpty()) {
                        processCollectedCells(graph, collectedCells);
                        collectedCells.clear();

                        collectedCells.add(arr[i][j]);
                    }
                }
            }
        }

        return graph;
    }


    private static void processCollectedCells(WeightedGraph<Vertex> graph, ArrayList<Vertex> cells) {
        //Remove walls from consideration
        for (Vertex v : cells) {
            if (v.c == 'w') {
                cells.remove(v);
            }
        }

        if (cells.size() < 2) {
            return; // No edges to add if less than 2 cells
        }

        graph.addEdge(cells.getFirst(), cells.getLast(), cells.size() - 1);
    }

    public static Vertex[][] transpose2DArray(Vertex[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        Vertex[][] transposed = new Vertex[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    /**
     * This is turning the file into  2d Array, separated by each character.
     * @param file
     * @return
     */
    private static Vertex[][] to2DArray(List<String> file) {
        Vertex[][] result = new Vertex[file.size()][file.get(0).length()];

        for (int i = 0; i < file.size(); i++) {
            String line = file.get(i);

            for (int j = 0; j < line.length(); j++) {
                result[i][j] = new Vertex(line.charAt(j), j, i);
            }
        }
        return result;
    }

    /**
     * method to get fileArray in another file.
     * @return
     */
    public Vertex[][] getFileArray(){
        return fileArray;
    }

    private static ArrayList<Long> measureFunctionTime(int numSamples, Runnable func) {
        ArrayList<Long> runtimes = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            long startTime = System.nanoTime();
            func.run();
            long endTime = System.nanoTime();

            runtimes.add((endTime - startTime));
        }

        return runtimes;
    }

    private static long meanOfRuntimes(ArrayList<Long> runtimes) {
        long total = 0;

        for (Long runtime : runtimes) {
            total += runtime;
        }

        return total / runtimes.size();
    }

    private static long nanoToMs(long nano) {
        return nano / 1_000_000;
    }
}