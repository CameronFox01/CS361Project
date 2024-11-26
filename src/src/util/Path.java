package util;

import DataStructures.ArrayList;

public class Path {
    private int weight;
    private final ArrayList<Vertex> path;
    private boolean fullPath = false;

    public Path() {
        weight = 0;
        path = new ArrayList<>();
    }

    public Path(Path other) {
        weight = other.weight;
        path = new ArrayList<>(other.path);
    }

    public Path(int weight, ArrayList<Vertex> path) {
        this.weight = weight;
        this.path = new ArrayList<>(path);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isFullPath() {
        return fullPath;
    }

    public void setFullPath(boolean fullPath) {
        this.fullPath = fullPath;
    }

    public void add(Vertex vertex) {
        path.add(vertex);
    }

    public ArrayList<Vertex> getPath() {
        return path;
    }

    public void removeLast() {
        path.removeLast();
    }

    public void displayPath(Vertex[][] fileArray) {
        Character[][] pathArr = new Character[fileArray.length][fileArray[0].length];

        for (int i = 0; i < pathArr.length; i++) {
            for (int j = 0; j < pathArr[0].length; j++) {
                pathArr[i][j] = '0';
            }
        }

        for (Vertex vertex : getPath()) {
            pathArr[vertex.y][vertex.x] = '1';
        }

        System.out.println("Path distance: " + weight);

        for (Character[] characters : pathArr) {
            for (int j = 0; j < pathArr[0].length; j++) {
                System.out.print(characters[j]);
            }
            System.out.println();
        }
    }

    //@Override
    //public String toString() {
    //    return ("Weight: " + weight + ", " + path);
   // }
}
