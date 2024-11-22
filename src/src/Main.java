import util.Edge;
import util.Vertex;

import java.io.File;
import DataStructures.*;
import util.WeightedEdge;

import java.util.Scanner;

/**
 * The File is read in as a command line argument.
 */
public class Main {
    private static  Vertex[][] fileArray;

    public static void main(String[] args) {
        List<String> fileString = new ArrayList<>();
        try {
            File file = new File(System.getProperty("user.dir") + "/TestCases/" + args[0]);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()){
                fileString.add(sc.nextLine());
            }

        } catch (Exception e){
            System.out.println(e);
        }

        fileArray = to2DArray(fileString);

        WeightedGraph<Vertex> graph = arrayToGraph(fileArray);

        // Print the 2D array to verify
        for (Vertex[] row : fileArray) {
            for (Vertex element : row) {
                System.out.print(element.c);
            }
            System.out.println();
        }

        //Transpose
        Vertex[][] transposedArr = transpose2DArray(fileArray);

        WeightedGraph<Vertex> verGraph = arrayToGraph(transposedArr);

        //Add vertical edges to graph
        for (Edge<Vertex, Vertex> edge : verGraph.getEdges()) {
            graph.addEdge((WeightedEdge<Vertex, Vertex>) edge);
        }
    }

    private static WeightedGraph<Vertex> arrayToGraph(Vertex[][] arr) {
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

        WeightedEdge<Vertex, Vertex> newEdge = new WeightedEdge<>();
        //Set vertices
        newEdge.setFst(cells.getFirst());
        newEdge.setSnd(cells.getLast());
        //Set weight
        newEdge.setWeight(cells.size() - 1);

        graph.addEdge(newEdge);
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
}