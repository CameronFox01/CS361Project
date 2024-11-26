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

    @Override
    public void add(T o) {
        if (!contains(o)) {
            super.add(o);
        }
    }

    public boolean tryAdd(T o) {
        if (!contains(o)) {
            super.add(o);
            return true;
        } else {
            return false;
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
