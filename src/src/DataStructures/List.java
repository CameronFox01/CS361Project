package DataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class List<T> implements Iterable<T> {
    protected T[] arr;
    protected int size;

    @SuppressWarnings("unchecked")
    public List() {
        arr = (T[]) new Object[10];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public List(int size) {
        arr = (T[]) new Object[size+1];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public List(List<T> other) {
        arr = (T[]) new Object[other.size+1];

        System.arraycopy(other.arr, 0, arr, 0, other.size());
        this.size = other.size();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void add(T object) {
        if (size == arr.length) {
            //Resize
            resize();
        }

        arr[size++] = object;
    }

    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        } else {
            return arr[index];
        }
    }

    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return arr[0];
        }
    }

    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return arr[size-1];
        }
    }

    public boolean contains(T object) {
        for (T item : this) {
            if (item == null) continue;
            if (item.equals(object)) {
                return true;
            }
        }

        return false;
    }

    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        } else {
            T item = arr[index];

            shiftFromIndex(index);
            size--;

            return item;
        }
    }

    public boolean remove(T o) {
        if (contains(o)) {
            remove(indexOf(o));
            return true;
        } else {
            return false;
        }
    }

    public T removeFirst() {
        return remove(0);
    }

    public T removeLast() {
        return remove(size - 1);
    }

    protected int indexOf(T o) {
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (arr[i] == o) {
                    return i;
                }
            }
        }

        return -1;
    }

    private void shiftFromIndex(int index) {
        for (int i = index + 1; i < size; i++) {
            arr[i - 1] = arr[i];
        }
    }

    @SuppressWarnings("unchecked")
    protected void resize() {
        //Double capacity of array
        T[] newArr = (T[]) new Object[size * 2];

        //Copy original array objects into new array
        if (size >= 0) System.arraycopy(arr, 0, newArr, 0, size);

        arr = newArr;
    }

    protected void trimToSize() {
        arr = toArray();
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        arr = (T[]) new Object[10];

        size = 0;
    }

    /**
     * Sets the array reference of this List's array to a new array.
     * This method is inherently unsafe so should be used with caution.
     * The length of array "as" should be the same as the amount of elements contained.
     * @param as New array
     */
    protected void setArray(T[] as) {
        arr = as;
        size = as.length;
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
