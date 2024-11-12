package DataStructures;

import util.Edge;

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

    public Collection<T> getVertices() {
        return vertices;
    }

    public Collection<Edge<T, T>> getEdges() {
        return edges;
    }

    public Set<T> verticesAdjacentTo(T v) {
        Set<T> connectedEdges = new Set<>();

        //Collect all edges
        for (Edge<T, T> edge : edges) {
            if (edge.getFst().equals(v)) {
                connectedEdges.add(edge.getSnd());
            } else if (edge.getSnd().equals(v)) {
                connectedEdges.add(edge.getFst());
            }
        }

        return connectedEdges;
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
    
    public void displayGraph() {
        for (T vertex : vertices) {
            System.out.print("Vertex " + vertex + " -> ");

            for (T adjacent : verticesAdjacentTo(vertex)) {
                System.out.print(adjacent + " ");
            }

            System.out.println();
        }
    }
}
