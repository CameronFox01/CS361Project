package DataStructures;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Collection <T> implements Iterable<T> {
    protected T[] arr;
    protected int size;

    @SuppressWarnings("unchecked")
    public Collection() {
        arr = (T[]) new Object[10];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    protected void addLast(T object) {
        if (size == arr.length) {
            //Resize
            resize();
        }

        arr[size++] = object;
    }



    @SuppressWarnings("unchecked")
    protected void resize() {
        //Double capacity of array
        T[] newArr = (T[]) new Object[size * 2];

        //Copy original array objects into new array
        if (size >= 0) System.arraycopy(arr, 0, newArr, 0, size);

        arr = newArr;
    }



    @SuppressWarnings("unchecked")
    public T[] toArray() {
        if (size == 0) {
            return (T[]) new Object[0];
        } else {
            T[] newArr = (T[]) new Object[size];

            System.arraycopy(arr, 0, newArr, 0, size);

            return newArr;
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        } else if (size == 1) {
            return "[" + arr[0] + "]";
        } else {
            StringBuilder builder = new StringBuilder("[");

            for (int i = 0; i < size - 1; i++) {
                builder.append(arr[i]).append(", ");
            }

            builder.append(arr[size - 1]).append("]");

            return builder.toString();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int currIndex = 0;

            @Override
            public boolean hasNext() {
                return currIndex < size;
            }

            @Override
            public T next() {
                return arr[currIndex++];
            }
        };
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
