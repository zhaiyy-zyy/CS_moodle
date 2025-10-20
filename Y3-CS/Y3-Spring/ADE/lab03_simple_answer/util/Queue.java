package ace.util;

public interface Queue<E> extends Collection<E> {
    // Operations defined in the textbook.

    /**
     * Appends the specified element to the top of this queue.
     *
     * @param e element to be appended to this queue
     * @return {@code true}
     */
    boolean enqueue(E e);

    /**
     * Removes and returns the element at the bottom of this queue.
     *
     * @return bottom element in the queue (or null if empty)
     */
    E dequeue();

    /**
     * Returns the element at the bottom of this queue.
     *
     * @return bottom element in the queue (or null if empty)
     */
    E first();
}
