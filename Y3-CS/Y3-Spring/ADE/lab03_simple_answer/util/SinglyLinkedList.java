package ace.util;

public class SinglyLinkedList<E> implements List<E> {
    /**
     * Nested {@code Node<E>} class
     */
    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    /**
     * Head node of the list (or null if empty).
     */
    private Node<E> head = null;

    /**
     * Last node of the list (or null if empty).
     */
    private Node<E> tail = null;

    /**
     * number of nodes in the list.
     */
    private int size = 0;

    /**
     * Constructs an initially empty list.
     */
    public SinglyLinkedList() {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E first() {
        if (isEmpty())
            return null;
        return head.getElement();
    }

    @Override
    public E last() {
        if (isEmpty())
            return null;
        return tail.getElement();
    }

    @Override
    public boolean addFirst(E e) {
        head = new Node<E>(e, head);

        if (isEmpty())
            tail = head;

        size++;

        return true;
    }

    @Override
    public boolean addLast(E e) {
        Node<E> node = new Node<E>(e, null);

        if (isEmpty())
            head = node;
        else
            tail.setNext(node);
        tail = node;

        size++;

        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty())
            return null;

        E e = head.getElement();

        head = head.getNext();
        size--;

        if (isEmpty()) {
            tail = null;
        }

        return e;
    }

    @Override
    public E removeLast() {
        // TODO (OPTIONAL) implement method removeLast()
        return null;
    }

    // NOTE: OPTIONAL ops.

    @Override
    public boolean add(E e) {
        // TODO (OPTIONAL) implement method add(E e)
        return false;
    }

    @Override
    public void add(int index, E element) {
        // TODO (OPTIONAL) implement method add(int index, E element)

    }

    @Override
    public E get(int index) {
        // TODO (OPTIONAL) implement method get(int index)
        return null;
    }

    @Override
    public E set(int index, E element) {
        // TODO (OPTIONAL) implement method set(int index, E element)
        return null;
    }

    @Override
    public boolean remove(Object o) {
        // TODO (OPTIONAL) implement method remove(Object o)
        return false;
    }

    @Override
    public E remove(int index) {
        // TODO (OPTIONAL) implement method remove(int index)
        return null;
    }
}
