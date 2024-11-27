package DataStructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class MinHeap<T> {
    private final ArrayList<T> heap;
    private final Comparator<? super T> comparator;

    public MinHeap(Comparator<? super T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        this.comparator = comparator;
        this.heap = new ArrayList<>();
    }

    public void add(T element) {
        heap.add(element);
        restoreHeapPropertyPostAddition();
    }

    public boolean contains(T o, Comparator<T> comparator) {
        for (T contained : heap) {
            if (comparator.compare(contained, o) == 0) {
                return true;
            }
        }

        return false;
    }

    public T peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public T poll() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        if (heap.size() == 1) {
            return heap.remove(0);
        }

        // Remove root and replace it with the last element
        T root = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        restoreHeapPropertyPostPop();
        return root;
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private int leftChildFromIndex(int i) {
        return 2 * i + 1;
    }

    private int rightChildFromIndex(int i) {
        return 2 * i + 2;
    }

    private void restoreHeapPropertyPostAddition() {
        int currentIndex = heap.size() - 1;

        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;

            if (comparator.compare(heap.get(parentIndex), heap.get(currentIndex)) <= 0) {
                break;
            }

            swap(currentIndex, parentIndex);
            currentIndex = parentIndex;
        }
    }

    private void restoreHeapPropertyPostPop() {
        int currentIndex = 0;

        while (true) {
            int leftIndex = leftChildFromIndex(currentIndex);
            int rightIndex = rightChildFromIndex(currentIndex);
            int smallestIndex = currentIndex;

            if (leftIndex < heap.size() &&
                    comparator.compare(heap.get(leftIndex), heap.get(smallestIndex)) < 0) {
                smallestIndex = leftIndex;
            }

            if (rightIndex < heap.size() &&
                    comparator.compare(heap.get(rightIndex), heap.get(smallestIndex)) < 0) {
                smallestIndex = rightIndex;
            }

            if (smallestIndex == currentIndex) {
                break;
            }

            swap(currentIndex, smallestIndex);
            currentIndex = smallestIndex;
        }
    }

    public ArrayList<T> toList() {
        return new ArrayList<>(heap);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
