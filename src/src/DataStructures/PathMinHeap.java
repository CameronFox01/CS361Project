package DataStructures;

import util.Path;

import java.util.NoSuchElementException;

public class PathMinHeap {
    private final ArrayList<Path> heap;

    public PathMinHeap() {
        heap = new ArrayList<>();
    }

    public void add(Path o) {
        //Reject null, and non-full paths
        if (o == null || !o.isFullPath()) return;

        heap.add(o);

        restoreHeapPropertyPostAddition();
    }

    public Path peek() {
        if (heap.isEmpty()) {
            return null;
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

    public Path poll() {
        if (heap.isEmpty()) {
            return null;
        }

        if (heap.size() == 1) {
            return heap.removeFirst();
        }

        // Remove head and replace with last element
        Path head = heap.getFirst();
        heap.set(0, heap.removeLast());

        restoreHeapPropertyPostPop();

        return head;
    }


    private void swap(int i, int j) {
        Path temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private int leftChildFromIndex(int i) {
        return i * 2 + 1;
    }

    private int rightChildFromIndex(int i) {
        return i * 2 + 2;
    }

    private Path getLeftChildFromIndex(int index) {
        return (heap.size() > index * 2 + 1) ? heap.get(leftChildFromIndex(index)) : null;
    }

    private Path getRightChildFromIndex(int index) {
        return (heap.size() > index * 2 + 2) ? heap.get(rightChildFromIndex(index)) : null;
    }

    private void restoreHeapPropertyPostAddition() {
        int currNode = heap.size() - 1;

        //While parent is larger, adjust
        while(currNode != 0 && greaterThan(heap.get(Math.floorDiv(currNode - 1, 2)), heap.get(currNode))) {
            //Swap these
            Path temp = heap.get(Math.floorDiv(currNode - 1, 2));
            heap.set(Math.floorDiv(currNode - 1, 2), heap.get(currNode));
            heap.set(currNode, temp);

            currNode = Math.floorDiv(currNode - 1, 2);
        }
    }

    private void restoreHeapPropertyPostPop() {
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
    }

    private boolean greaterThan(Path p1, Path p2) {
        return (p1.getWeight() > p2.getWeight());
    }

    public List<Path> toList() {
        return new List<>(heap);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
