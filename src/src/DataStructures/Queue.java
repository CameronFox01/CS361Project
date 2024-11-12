package DataStructures;

import java.util.NoSuchElementException;

public class Queue<T> extends Collection<T> {

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
            return arr[0];
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return arr[0];
        }
    }

    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            T item = arr[0];

            for (int i = 1; i < size; i++) {
                arr[i-1] = arr[i];
            }

            arr[size-1] = null;

            size--;

            return item;
        }
    }
}
