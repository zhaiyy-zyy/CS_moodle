package ace.util;

public class ArrayStack<E> implements Stack<E> {
    public static final int CAPACITY = 1000;

    private E[] data;

    private int idx = -1;

    public ArrayStack() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return idx + 1;
    }

    @Override
    public boolean isEmpty() {
        return (idx == -1);
    }

    @Override
    public boolean push(E e) throws IllegalStateException {
        if (size() == data.length)
            throw new IllegalStateException("Stack is full");
        data[++idx] = e;

        return true;
    }

    @Override
    public E pop() {
        if (isEmpty())
            return null;

        E e = data[idx];
        data[idx] = null;
        idx--;

        return e;
    }

    @Override
    public E top() {
        if (isEmpty())
            return null;

        return data[idx];
    }

}
