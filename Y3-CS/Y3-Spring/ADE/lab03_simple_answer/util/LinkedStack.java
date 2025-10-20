package ace.util;

public class LinkedStack<E> extends SinglyLinkedList<E> implements Stack<E> {
    public LinkedStack() {
        super();
    }

    @Override
    public boolean push(E e) {
        return addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E top() {
        return first();
    }
}
