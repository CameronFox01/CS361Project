package DataStructures;

import util.Node;

import java.util.NoSuchElementException;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return head.getVal();
        }
    }

    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        return tail.getVal();
    }

    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            T toReturn = head.getVal();

            head = null;
            tail = null;

            size = 0;
            return toReturn;

        } else {
            T toReturn = head.getVal();

            head = head.getNext();
            head.setPrevious(tail);
            tail.setNext(head);

            size--;
            return toReturn;
        }
    }

    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            return removeFirst();
        } else {
            T toReturn = tail.getVal();

            tail = tail.getPrevious();
            tail.setNext(head);
            head.setPrevious(tail);

            size--;
            return toReturn;
        }
    }

    public void addLast(T newVal) {
        Node<T> newNode = new Node<>(newVal);

        if (size == 0) {
            head = newNode;
            tail = newNode;
            newNode.setPrevious(newNode);
            newNode.setNext(newNode);
            size = 1;
        } else {
            tail.setNext(newNode);
            head.setPrevious(newNode);

            newNode.setPrevious(tail);
            newNode.setNext(head);

            tail = newNode;
            size++;
        }
    }
}
