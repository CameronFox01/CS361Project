package DataStructures;

import util.Node;

import java.util.NoSuchElementException;

public class PriorityQueue<T extends Comparable<T>> extends LinkedList<T>{
    public PriorityQueue(){
        super();
    }

    public void offer(T o){
        if(isEmpty()){
            addLast(o);
        } else {
            Node<T> current = getHead();
            Node<T> head = getHead();
            while (current != null && current.getVal().compareTo(o) <= 0) {
                current = current.getNext();
                if (current == head) break; // Circular loop
            }
            if (current == null) {
                addLast(o); // Add to the end if all elements are smaller
            } else {
                Node<T> newNode = new Node<>(o);
                Node<T> prev = current.getPrevious();
                prev.setNext(newNode);
                newNode.setPrevious(prev);
                newNode.setNext(current);
                current.setPrevious(newNode);
                if (current == head && o.compareTo(current.getVal()) < 0) {
                    head = newNode; // Update head if new node is smallest
                }
                setSize();
            }
        }
    }

    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return getFirst();
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return getFirst();
        }
    }

    public T poll() {
        return removeFirst();
    }
}
