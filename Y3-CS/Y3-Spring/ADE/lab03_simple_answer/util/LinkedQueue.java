package ace.util;

public class LinkedQueue<E> extends SinglyLinkedList<E> implements Queue<E> {
    public LinkedQueue() {
        super();
    }

    @Override
    public boolean enqueue(E e) {
        return addLast(e);
    }

    @Override
    public E dequeue() {
        return removeFirst();
    }
}
