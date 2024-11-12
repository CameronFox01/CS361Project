package DataStructures;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Set<T> implements Iterable<T> {
    private final ArrayList<T> set;

    public Set() {
        set = new ArrayList<>();
    }

    public int size() {
        return set.size();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public void add(T o) {
        if (!contains(o)) {
            set.add(o);
        }
    }

    public void clear() {
        set.clear();
    }

    public boolean contains(T o) {
        return set.contains(o);
    }

    public boolean remove(T o) {
        if (contains(o)) {
            set.remove(o);
            return true;
        } else {
            return false;
        }
    }

    public T[] toArray() {
        return set.toArray();
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }
}
