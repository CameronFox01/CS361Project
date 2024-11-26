package DataStructures;

import util.Edge;
import util.Vertex;
import util.WeightedEdge;

public class Graph<T> {
    private final Set<T> vertices;
    private final Set<Edge<T, T>> edges;

    public Graph() {
        vertices = new Set<>();
        edges = new Set<>();
    }

    protected void addEdge(Edge<T, T> edge) {
        addVertex(edge.getFst());
        addVertex(edge.getSnd());

        edges.add(edge);
    }

    public void addVertex(T v) {
        vertices.add(v);
    }

    public List<T> getVertices() {
        return vertices;
    }

    public List<Edge<T, T>> getEdges() {
        return edges;
    }

    public boolean containsVertex(T v) {
        return vertices.contains(v);
    }

    public boolean containsEdge(T vertex1, T vertex2) {
        return edges.contains(new Edge<>(vertex1, vertex2));
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    public int size() {
        return vertices.size();
    }

    public int edgeCount() {
        return edges.size();
    }
}
