package DataStructures;

import java.util.NoSuchElementException;

public class ArrayList <T> extends List<T> {

    public ArrayList() {
        super();
    }

    public ArrayList(int size) {
        super(size);
    }

    public ArrayList(List<T> other) {
        super(other);
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

    public int indexOf(T o) {
        return super.indexOf(o);
    }

    @Override
    public void trimToSize() {
        super.trimToSize();
    }
}
