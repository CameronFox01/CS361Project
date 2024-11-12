package DataStructures;

public class Set<T> extends Collection<T> {
    public Set() {
        super();
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
