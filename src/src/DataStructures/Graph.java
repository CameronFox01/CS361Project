package DataStructures;

import util.Edge;
import util.Vertex;

public class Graph<T> {
    private final Set<T> vertices;
    private final Set<Edge<T, T>> edges;
    private final Map<T, Set<T>> adjancecyMap;

    public Graph() {
        adjancecyMap = new Map<>();
        vertices = new Set<>();
        edges = new Set<>();
    }

    protected void addEdge(Edge<T, T> edge) {
        addVertex(edge.getFst());
        addVertex(edge.getSnd());

        adjancecyMap.get(edge.getFst()).add(edge.getSnd());
        adjancecyMap.get(edge.getSnd()).add(edge.getFst());

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

    public Set<T> verticesAdjacentTo(T v) {
        if (!containsVertex(v)) return null;

        return adjancecyMap.get(v);
    }

    public Map<T, Set<T>> getAdjancecyMap() {
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

            for (T adjacent : verticesAdjacentTo(vertex)) {
                System.out.print(adjacent + " ");
            }

            System.out.println();
        }
    }
}
