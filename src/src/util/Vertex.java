package util;

public class Vertex {
    public final Character c;
    public final int x;
    public final int y;

    public Vertex(Character c, int x, int y) {
        this.c = c;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.valueOf("(c:" + c + ", x:" + x + ", y:" + y + ")");
    }
}
