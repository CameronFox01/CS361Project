package DataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ArrayList <T> implements Iterable<T> {
    private T[] arr;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        arr = (T[]) new Object[10];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        } else {
            return arr[index];
        }
    }

    public void add(T object) {
        if (size == arr.length) {
            //Resize
            resize();
        }

        arr[size++] = object;
    }

    public void add(T o, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        //Check if we need to resize the array
        if (size == arr.length) {
            resize();
        }

        //Shift elements to the right starting from the last element down to the index
        for (int i = size; i > index; i--) {
            arr[i] = arr[i - 1];
        }

        //Insert the element at the specified index
        arr[index] = o;
        size++;
    }

    public void set(int index, T object) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        } else {
            arr[index] = object;
        }
    }

    public int indexOf(T o) {
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (get(i) == o) {
                    return i;
                }
            }

        }

        return -1;
    }

    public T remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        } else {
            T item = arr[index];

            shiftFromIndex(index);
            size--;

            return item;
        }
    }

    public boolean remove(T o) {
        for(int i = 0; i < size; i++) {
            if (arr[i] == o) {
                shiftFromIndex(i);
                size--;

                return true;
            }
        }

        return false;
    }

    private void shiftFromIndex(int index) {
        for (int i = index + 1; i < size; i++) {
            arr[i - 1] = arr[i];
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
            if (item == object) {
                return true;
            }
        }

        return false;
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

    @SuppressWarnings("unchecked")
    private void resize() {
        //Double capacity of array
        T[] newArr = (T[]) new Object[size * 2];

        //Copy original array objects into new array
        if (size >= 0) System.arraycopy(arr, 0, newArr, 0, size);

        arr = newArr;
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
