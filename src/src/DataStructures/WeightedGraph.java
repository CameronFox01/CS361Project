package DataStructures;

import util.Edge;
import util.Vertex;
import util.WeightedEdge;

public class WeightedGraph<T> extends Graph<T> {
    private final Map<T, Set<WeightedEdge<T, T>>> adjancecyMap;

    public WeightedGraph() {
        super();
        adjancecyMap = new Map<>();
    }

    public void addEdge(T vertex1, T vertex2, int weight) {
        WeightedEdge<T, T> newEdge = new WeightedEdge<>(vertex1, vertex2, weight);

        super.addEdge(newEdge);

        if (!adjancecyMap.containsKey(vertex1)) {
            adjancecyMap.put(vertex1, new Set<>());
        }

        if (!adjancecyMap.containsKey(vertex2)) {
            adjancecyMap.put(vertex2, new Set<>());
        }

        adjancecyMap.get(vertex1).add(newEdge);
        adjancecyMap.get(vertex2).add(newEdge);
    }

    public void addEdge(WeightedEdge<T, T> edge) {
        super.addEdge(edge);
    }

    public Set<WeightedEdge<T, T>> verticesAdjacentTo(T v) {
        if (!containsVertex(v)) return null;

        return adjancecyMap.get(v);
    }

    public void displayGraph() {
        for (T vertex : getVertices()) {
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
