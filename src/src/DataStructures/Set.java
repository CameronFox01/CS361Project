package DataStructures;

public class Set<T> extends List<T> {
    public Set() {
        super();
    }

    public Set(List<T> other) {
        super(other);
    }

    public void add(T o) {
        if (!contains(o)) {
            addLast(o);
        }
    }

    public boolean remove(T o) {
        if (contains(o)) {
            super.remove(indexOf(o));
            return true;
        } else {
            return false;
        }
    }
}
