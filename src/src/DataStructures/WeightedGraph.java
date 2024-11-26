package DataStructures;

import util.Edge;
import util.Vertex;
import util.WeightedEdge;

public class WeightedGraph<T> {
    private final Set<T> vertices;
    private final Set<WeightedEdge<T, T>> edges;
    private final Map<T, Set<WeightedEdge<T, T>>> adjancecyMap;

    public WeightedGraph() {
        adjancecyMap = new Map<>();
        vertices = new Set<>();
        edges = new Set<>();
    }

    public void addEdge(WeightedEdge<T, T> edge) {
        addVertex(edge.getFst());
        addVertex(edge.getSnd());

        adjancecyMap.get(edge.getFst()).add(edge);
        adjancecyMap.get(edge.getSnd()).add(edge);

        edges.add(edge);
    }

    public void addVertex(T v) {
        vertices.add(v);

        if (!adjancecyMap.containsKey(v)) {
            adjancecyMap.put(v, new Set<>());
        }
    }

    public List<T> getVertices() {
        return vertices;
    }

    public void addEdge(T vertex1, T vertex2, int weight) {
        WeightedEdge<T, T> newEdge = new WeightedEdge<>(vertex1, vertex2, weight);

        addEdge(newEdge);

        if (!adjancecyMap.containsKey(vertex1)) {
            adjancecyMap.put(vertex1, new Set<>());
        }

        if (!adjancecyMap.containsKey(vertex2)) {
            adjancecyMap.put(vertex2, new Set<>());
        }

        adjancecyMap.get(vertex1).add(newEdge);
        adjancecyMap.get(vertex2).add(newEdge);
    }

    public List<WeightedEdge<T, T>> getEdges() {
        return edges;
    }

    public Set<WeightedEdge<T, T>> verticesAdjacentTo(T v) {
        if (!containsVertex(v)) return null;

        return adjancecyMap.get(v);
    }

    public boolean containsVertex(T v) {
        return vertices.contains(v);
    }

    public boolean containsEdge(T vertex1, T vertex2) {
        return edges.contains(new WeightedEdge<>(vertex1, vertex2, -1));
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
            System.out.println("Vertex " + vertex + " -> ");

            for (Edge<T, T> edgeOf : verticesAdjacentTo(vertex)) {
                if (edgeOf.getFst().equals(vertex)) {
                    System.out.println(edgeOf.getSnd() + " ");
                } else {
                    System.out.println(edgeOf.getFst() + " ");
                }
            }

            System.out.println();
        }
    }
}
