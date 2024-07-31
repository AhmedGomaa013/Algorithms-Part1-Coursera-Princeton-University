import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;
    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 0;
        this.queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size > 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        this.validateAdd(item);
        if (this.size == this.queue.length) {
            this.resizeQueue(2 * this.queue.length);
        }
        this.queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        validateRemove();
        int rand = StdRandom.uniformInt(this.size);
        Item removedItem = this.queue[rand];
        this.queue[rand] = this.queue[this.size - 1];
        this.queue[this.size - 1] = null;
        this.size--;
        if (this.size <= this.queue.length / 4) {
            this.resizeQueue(this.queue.length / 2);
        }

        return removedItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validateRemove();
        int rand = StdRandom.uniformInt(this.size);
        Item item = this.queue[rand];

        return item;
    }

    private void validateAdd(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    private void validateRemove() {
        if (this.size == 0) throw new java.util.NoSuchElementException();
    }

    private void resizeQueue(int newCapasity) {
        Item[] newQueue = (Item[]) new Object[newCapasity];
        for (int i = 0; i < this.size; i++) {
            newQueue[i] = this.queue[i];
        }

        this.queue = newQueue;
    }
    // return an independent iterator over items in random order
    public java.util.Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(this);
    }

    private class RandomizedQueueIterator<Item> implements java.util.Iterator<Item> {
        private int size;
        private Item[] items;
        public RandomizedQueueIterator(RandomizedQueue<Item> queue) {
            this.size = queue.size;
            this.items = queue.queue;
        }
        public boolean hasNext() {
            return this.size > 0;
        }

        public Item next() {
            if (this.size <= 0) throw new NoSuchElementException();
            int rand = StdRandom.uniformInt(this.size);
            Item item = this.items[rand];
            this.items[rand] = this.items[this.size - 1];
            this.items[this.size - 1] = item;
            this.size--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    // unit testing (required)
    public static void main(String[] args) {}

}