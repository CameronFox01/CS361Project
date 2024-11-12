package DataStructures;

import java.util.NoSuchElementException;

public class ArrayList <T> extends Collection<T> {

    public ArrayList() {
        super();
    }

    public ArrayList(int size) {
        super(size);
    }

    public ArrayList(ArrayList<T> other) {
        super(other.size());
        size = other.size();

        System.arraycopy(other.arr, 0, arr, 0, other.size());
    }

    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        } else {
            return arr[index];
        }
    }

    public void add(T o) {
        addLast(o);
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        } else {
            arr[index] = object;
        }
    }


    public T remove(int index) {
        return super.remove(index);
    }

    public int indexOf(T o) {
        return super.indexOf(o);
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

    @Override
    public void trimToSize() {
        super.trimToSize();
    }
}
