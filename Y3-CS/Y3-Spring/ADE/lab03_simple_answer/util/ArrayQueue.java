package ace.util;

public class ArrayQueue<E> implements Queue<E> {
    private static int CAPACITY = 1000;

    /**
     * array for storage
     */
    private E[] data;

    /**
     * index of the front element
     */
    private int f = 0;

    /**
     * current number of elements
     */
    private int sz = 0;

    public ArrayQueue() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        data = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return sz == 0;
    }

    @Override
    public boolean enqueue(E e) throws IllegalStateException {
        if (sz == data.length)
            throw new IllegalStateException("Queue is full");

        int idx = (f + sz) % data.length;

        data[idx] = e;

        sz++;

        return true;
    }

    @Override
    public E dequeue() {
        if (isEmpty())
            return null;

        E e = data[f];

        data[f] = null;
        f = (f + 1) % data.length;
        sz--;

        return e;
    }

    @Override
    public E first() {
        if (isEmpty())
            return null;

        return data[f];
    }
}
