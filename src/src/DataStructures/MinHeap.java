package DataStructures;

import java.util.NoSuchElementException;

public class MinHeap<Number> {
    private final ArrayList<Number> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    public void add(Number o) {
        heap.add(o);

        restoreHeapPropertyPostAddition();
    }

    public Number peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return heap.getFirst();
        }
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public Number poll() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap empty");
        }

        if (heap.size() == 1) {
            return heap.removeFirst();
        }

        // Remove head and replace with last element
        Number head = heap.getFirst();
        heap.set(0, heap.removeLast());

        //Restore heap property
        int currIndex = 0;
        while (true) {
            int leftIndex = leftChildFromIndex(currIndex);
            int rightIndex = rightChildFromIndex(currIndex);

            //If no children, we're done
            if (leftIndex >= heap.size()) {
                break;
            }

            //Determine the smaller child
            int minIndex = leftIndex;
            if (rightIndex < heap.size() && greaterThan(heap.get(leftIndex), heap.get(rightIndex))) {
                minIndex = rightIndex;
            }

            //Swap if the current node is greater than the smaller child
            if (greaterThan(heap.get(currIndex), heap.get(minIndex))) {
                swap(currIndex, minIndex);
                currIndex = minIndex;
            } else {
                break;
            }
        }

        return head;
    }


    private void swap(int i, int j) {
        Number temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private int leftChildFromIndex(int i) {
        return i * 2 + 1;
    }

    private int rightChildFromIndex(int i) {
        return i * 2 + 2;
    }

    private Number getLeftChildFromIndex(int index) {
        return (heap.size() > index * 2 + 1) ? heap.get(leftChildFromIndex(index)) : null;
    }

    private Number getRightChildFromIndex(int index) {
        return (heap.size() > index * 2 + 2) ? heap.get(rightChildFromIndex(index)) : null;
    }

    private void restoreHeapPropertyPostAddition() {
        int currNode = heap.size() - 1;

        //While parent is larger, adjust
        while(currNode != 0 && greaterThan(heap.get(Math.floorDiv(currNode - 1, 2)), heap.get(currNode))) {
            //Swap these
            Number temp = heap.get(Math.floorDiv(currNode - 1, 2));
            heap.set(Math.floorDiv(currNode - 1, 2), heap.get(currNode));
            heap.set(currNode, temp);

            currNode = Math.floorDiv(currNode - 1, 2);
        }
    }

    private boolean greaterThan(Number x, Number y) {
        switch (x) {
            case Integer int1 -> {
                int int2 = (Integer) y;

                return int1 > int2;
            }
            case Double double1 -> {
                double double2 = (Double) y;

                return double1 > double2;
            }
            case Float float1 -> {
                float float2 = (Float) y;

                return float1 > float2;
            }
            case Short short1 -> {
                short short2 = (Short) y;

                return short1 > short2;
            }
            case null, default -> {
                long long1 = (Long) x;
                long long2 = (Long) y;

                return long1 > long2;
            }
        }
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
