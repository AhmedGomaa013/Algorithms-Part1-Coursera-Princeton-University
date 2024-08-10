import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head, tail;
    private int size;
    
    private static class Node<Item> {
        Item data;
        Node<Item> next;
        Node<Item> prev;

        public Node(Item item) {
            this.next = null;
            this.prev = null;
            this.data = item;
        }
    }
    
    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        this.validateAdd(item);
        Node<Item> newHead = new Node<Item>(item);
        if (this.isEmpty()) {
            this.head = this.tail = newHead;
        }
        else {
            newHead.next = this.head;
            this.head.prev = newHead;
            this.head = newHead;
        }
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        this.validateAdd(item);
        Node<Item> newTail = new Node<Item>(item);
        if (this.isEmpty()) {
            this.tail = this.head = newTail;
        }
        else {
            this.tail.next = newTail;
            newTail.prev = this.tail;
            this.tail = newTail;
        }
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        this.validateRemove();
        Node<Item> removedData = this.head;
        this.size--;
        if (this.isEmpty()) {
            this.head = this.tail = null;
        }
        else {
            this.head = this.head.next;
            this.head.prev = null;
        }
        return removedData.data;

    }

    // remove and return the item from the back
    public Item removeLast() {
        this.validateRemove();
        Node<Item> removedData = this.tail;
        this.size--;
        if (this.isEmpty()) {
            this.head = this.tail = null;
        }
        else {
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
        return removedData.data;
    }

    private void validateRemove() {
        if (this.isEmpty()) throw new java.util.NoSuchElementException();
    }

    private void validateAdd(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }
    // return an iterator over items in order from front to back
    public java.util.Iterator<Item> iterator() {
        return new DequeIterator<>(this);
    }

    private class DequeIterator<Item> implements java.util.Iterator<Item> {
        Node<Item> current;
        public DequeIterator(Deque<Item> first) {
            current = first.head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item data = current.data;
            current = current.next;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    // unit testing (required)
    public static void main(String[] args) { }
}
