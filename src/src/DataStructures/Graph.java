package DataStructures;

import util.Edge;
import util.Vertex;
import util.WeightedEdge;

public class Graph<T> {
    private final Set<T> vertices;
    private final Set<Edge<T, T>> edges;
    private final Map<T, Set<Edge<T, T>>> adjancecyMap;

    public Graph() {
        adjancecyMap = new Map<>();
        vertices = new Set<>();
        edges = new Set<>();
    }

    protected void addEdge(Edge<T, T> edge) {
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

    public List<Edge<T, T>> getEdges() {
        return edges;
    }

    public Set<Edge<T, T>> verticesAdjacentTo(T v) {
        if (!containsVertex(v)) return null;

        return adjancecyMap.get(v);
    }

    public Map<T, Set<Edge<T, T>>> getAdjancecyMap() {
        return adjancecyMap;
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
