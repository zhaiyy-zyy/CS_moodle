package ace.util;

public class DoublyLinkedList<E> implements List<E> {
    /**
     * Nested {@code Node<E>} class
     */
    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> p) {
            prev = p;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    /**
     * Header sentinel
     */
    private Node<E> header = null;

    /**
     * Trailer sentinel
     */
    private Node<E> trailer = null;

    /**
     * number of nodes in the list.
     */
    private int size = 0;

    /**
     * Constructs an initially empty list.
     */
    public DoublyLinkedList() {
        header = new Node<E>(null, null, null);
        trailer = new Node<E>(null, header, null);
        header.setNext(trailer);
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

        // first element is beyond header
        return header.getNext().getElement();
    }

    @Override
    public E last() {
        if (isEmpty())
            return null;

        // last element is before trailer
        return trailer.getPrev().getElement();
    }

    private boolean addBetween(E e, Node<E> predecessor, Node<E> successor) {
        Node<E> node = new Node<E>(e, predecessor, successor);
        predecessor.setNext(node);
        successor.setPrev(node);

        size++;

        return true;
    }

    @Override
    public boolean addFirst(E e) {
        return addBetween(e, header, header.getNext());
    }

    @Override
    public boolean addLast(E e) {
        return addBetween(e, trailer.getPrev(), trailer);
    }

    private E remove(Node<E> node) {
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();

        predecessor.setNext(successor);
        successor.setPrev(predecessor);

        size--;

        return node.getElement();
    }

    @Override
    public E removeFirst() {
        if (isEmpty())
            return null;

        return remove(header.getNext());
    }

    @Override
    public E removeLast() {
        if (isEmpty())
            return null;

        return remove(trailer.getPrev());
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
