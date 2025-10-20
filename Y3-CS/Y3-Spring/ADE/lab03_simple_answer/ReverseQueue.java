public class ReverseQueue {

    public static <T> void reverseQueue(CustomQueue<T> queue) {
        if (queue.isEmpty()) {
            return;
        }

        T element = queue.dequeue();
        reverseQueue(queue);
        queue.enqueue(element);
    }

    public static void main(String[] args) {
        CustomQueue<Integer> queue = new CustomQueue<>(10);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        System.out.println("Original queue: ");
        for (Integer element : queue.getElements()) {
            System.out.print(element + " ");
        }
        System.out.println();

        reverseQueue(queue);

        System.out.println("Reversed queue: ");
        for (Integer element : queue.getElements()) {
            System.out.print(element + " ");
        }
    }
}