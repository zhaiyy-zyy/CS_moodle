package ace;

import ace.util.*;

public class JosephusProblem {
    public static int Solve(int n, int k) throws IllegalArgumentException {
        if (n <= 0 || k <= 0) {
            throw new IllegalArgumentException("invalid arguments");
        }

        // initialize the queue
        Queue<Integer> queue = new LinkedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);

        while (queue.size() > 1) {
            for (int i = 0; i < k - 1; i++) {
                queue.enqueue(queue.dequeue());
            }
            queue.dequeue();
        }

        return queue.dequeue();
    }
}
