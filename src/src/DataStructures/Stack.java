package DataStructures;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class Stack<T> extends LinkedList<T> {

    public Stack() {
        super();
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return getLast();
        }
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return removeLast();
        }
    }

    public T push(T o) {
        addLast(o);

        return o;
    }
}
