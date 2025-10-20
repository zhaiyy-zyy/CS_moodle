package ace.util;

public interface Stack<E> extends Collection<E> {
    // Operations defined in the textbook.

    /**
     * Appends the specified element to the top of this stack.
     *
     * @param e element to be appended to this stack
     * @return {@code true}
     */
    boolean push(E e);

    /**
     * Removes and returns the element at the top of this stack.
     *
     * @return top element in the stack (or null if empty)
     */
    E pop();

    /**
     * Returns the element at the top of this stack.
     *
     * @return top element in the stack (or null if empty)
     */
    E top();
}
