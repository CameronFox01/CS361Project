package util;

public class Node<T> {
    private Node<T> next;
    private Node<T> previous;
    private T val;

    public Node() {}

    public Node(T val) {
        this.val = val;
        next = null;
        previous = null;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Node<?> otherNode) {
            return otherNode.getVal() == val;
        } else {
            return false;
        }
    }
}
