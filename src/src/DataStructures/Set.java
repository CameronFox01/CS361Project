package DataStructures;

public class Set<T> extends List<T> {
    public Set() {
        super();
    }

    public Set(List<T> other) {
        Set<T> newSet = new Set<>();

        for (T o : other) {
            newSet.add(o);
        }

        setArray(newSet.toArray());
    }

    public void add(T o) {
        if (!contains(o)) {
            super.add(o);
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
