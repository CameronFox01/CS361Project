package DataStructures;

import util.WeightedEdge;

public class WeightedGraph<T> extends Graph<T> {
    public WeightedGraph() {
        super();
    }

    public void addEdge(T vertex1, T vertex2, int weight) {
        super.addEdge(new WeightedEdge<>(vertex1, vertex2, weight));
    }

    public void addEdge(WeightedEdge<T, T> edge) {
        super.addEdge(edge);
    }
}
