package ace.util;

/**
 * Common Operations shared with {@code ace.util.List}, {@code ace.util.Stack},
 * {@code ace.util.Queue}, etc.
 */
public interface Collection<E> {
    // Query Operations

    /**
     * Returns the number of elements in this collection. If this collection
     * contains
     * more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Returns {@code true} if this collection contains no elements.
     *
     * @return {@code true} if this collection contains no elements
     */
    boolean isEmpty();
}
