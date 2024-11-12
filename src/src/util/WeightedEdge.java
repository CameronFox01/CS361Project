package util;

public class WeightedEdge<T, V> extends Edge<T, V> {
    private int weight;

    public WeightedEdge() {
        super();
    }

    public WeightedEdge(T fst, V snd, int weight) {
        super(fst, snd);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
