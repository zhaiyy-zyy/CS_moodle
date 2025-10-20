import java.util.HashSet;

public class RemoveDuplicatesFromStack {

    public static <T> void removeDuplicates(CustomStack<T> stack) {
        HashSet<T> seen = new HashSet<>();
        CustomStack<T> tempStack = new CustomStack<>(stack.size());

        while (!stack.isEmpty()) {
            T element = stack.pop();
            if (!seen.contains(element)) {
                seen.add(element);
                tempStack.push(element);
            }
        }

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
    }

    public static void main(String[] args) {
        CustomStack<Integer> stack = new CustomStack<>(10);
        stack.push(1);
        stack.push(2);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(4);
        stack.push(5);

        System.out.println("Original stack: ");
        for (Integer element : stack.getElements()) {
            System.out.print(element + " ");
        }
        System.out.println();

        removeDuplicates(stack);

        System.out.println("Stack after removing duplicates: ");
        for (Integer element : stack.getElements()) {
            System.out.print(element + " ");
        }
    }
}