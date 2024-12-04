package util;

import DataStructures.ArrayList;

public class Path {
    private int weight;
    private final ArrayList<Vertex> path;
    private boolean isFullPath = false;

    public Path() {
        weight = 0;
        path = new ArrayList<>();
    }

    public Path(Path other) {
        weight = other.weight;
        path = new ArrayList<>(other.path);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isFullPath() {
        return isFullPath;
    }

    public void setFullPath() {
        this.isFullPath = true;
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

    public void reverse() {
        int left = 0;
        int right = path.size() - 1;

        // Swap elements from both ends of the path
        while (left < right) {
            Vertex temp = path.get(left);
            path.set(left, path.get(right));
            path.set(right, temp);
            left++;
            right--;
        }
    }

    public void displayPath(Vertex[][] fileArray) {
        Character[][] pathArr = new Character[fileArray.length][fileArray[0].length];

        for (int i = 0; i < pathArr.length; i++) {
            for (int j = 0; j < pathArr[0].length; j++) {
                pathArr[i][j] = (fileArray[i][j].c == '1') ? '1' : '.';
            }
        }

        int distance = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            distance--;
            Vertex start = path.get(i);
            Vertex end = path.get(i + 1);

            // If horizontal movement (same y-coordinate)
            if (start.y == end.y) {
                // Ensure we're moving in the correct direction
                if (start.x < end.x) {
                    for (int j = start.x; j <= end.x; j++) {
                        pathArr[start.y][j] = 'x';
                        distance++;
                    }
                } else {
                    for (int j = start.x; j >= end.x; j--) {
                        pathArr[start.y][j] = 'x';
                        distance++;
                    }
                }
            }
            // If vertical movement (same x-coordinate)
            else if (start.x == end.x) {
                // Ensure we're moving in the correct direction
                if (start.y < end.y) {
                    for (int j = start.y; j <= end.y; j++) {
                        pathArr[j][start.x] = 'x';
                        distance++;
                    }
                } else {
                    for (int j = start.y; j >= end.y; j--) {
                        pathArr[j][start.x] = 'x';
                        distance++;
                    }
                }
            }
        }

        System.out.println("Path distance: " + distance);

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
