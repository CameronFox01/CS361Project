package util;

public class Vertex {
    public final Character value;

    public Vertex(Character c) {
        value = c;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
