package DataStructures;

import java.util.NoSuchElementException;

public class Queue<T> extends LinkedList<T> {

    public Queue() {
        super();
    }

    public void offer(T o) {
        addLast(o);
    }

    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return getFirst();
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return getFirst();
        }
    }

    public T poll() {
        return removeFirst();
    }
}
