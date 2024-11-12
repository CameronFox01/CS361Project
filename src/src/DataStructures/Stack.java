package DataStructures;

import java.util.NoSuchElementException;

public class Stack<T> extends Collection<T> {

    public Stack() {
        super();
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack empty");
        } else {
            return arr[size-1];
        }
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack empty");
        } else {
            T item = arr[size-1];

            arr[size-1] = null;
            size--;

            return item;
        }
    }

    public T push(T o) {
        addLast(o);

        return o;
    }
}
