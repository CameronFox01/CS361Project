package DataStructures;

import util.Edge;

public class UnweightedGraph<T> extends Graph<T> {
    public UnweightedGraph() {
        super();
    }

    public void addEdge(T vertex1, T vertex2) {
        super.addEdge(new Edge<>(vertex1, vertex2));
    }
}
