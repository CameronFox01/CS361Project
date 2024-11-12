package util;

public class Edge<T, V> extends Pair<T, V> {
    public Edge() {
        super();
    }

    public Edge(T fst, V snd) {
        super(fst, snd);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Edge<?, ?> other) {
            return getFst() == other.getFst() && getSnd() == other.getSnd();
        } else {
            return false;
        }
    }
}
