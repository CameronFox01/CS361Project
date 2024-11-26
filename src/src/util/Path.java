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

    public Path(int weight) {
        this.weight = weight;
        this.path = new ArrayList<>();
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
                pathArr[i][j] = (fileArray[i][j].c == '1') ? '1' : '_';
            }
        }

        for (int i = 0; i < path.size() - 1; i++) {
            Vertex start = path.get(i);
            Vertex end = path.get(i + 1);

            // If horizontal movement (same y-coordinate)
            if (start.y == end.y) {
                // Ensure we're moving in the correct direction
                if (start.x < end.x) {
                    for (int j = start.x; j <= end.x; j++) {
                        if (pathArr[start.y][j] != '1') {
                            pathArr[start.y][j] = 'x';
                        }
                    }
                } else {
                    for (int j = start.x; j >= end.x; j--) {
                        if (pathArr[start.y][j] != '1') {
                            pathArr[start.y][j] = 'x';
                        }
                    }
                }
            }
            // If vertical movement (same x-coordinate)
            else if (start.x == end.x) {
                // Ensure we're moving in the correct direction
                if (start.y < end.y) {
                    for (int j = start.y; j <= end.y; j++) {
                        if (pathArr[j][start.x] != '1') {
                            pathArr[j][start.x] = 'x';
                        }
                    }
                } else {
                    for (int j = start.y; j >= end.y; j--) {
                        if (pathArr[j][start.x] != '1') {
                            pathArr[j][start.x] = 'x';
                        }
                    }
                }
            }
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
