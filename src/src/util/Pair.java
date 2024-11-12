package util;

public class Pair<T, V> {
    public final T fst;
    public final V snd;

    public Pair(T fst, V snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public String toString() {
        return "(" + fst + ", " + snd + ")";
    }
}
