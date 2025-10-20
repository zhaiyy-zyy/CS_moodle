import java.util.HashSet;
import java.util.Set;

public class CommonElementsInStacks {

    public static <T> Set<T> findCommonElements(CustomStack<T> stack1, CustomStack<T> stack2) {
        Set<T> commonElements = new HashSet<>();
        Set<T> elementsInStack1 = new HashSet<>();

        while (!stack1.isEmpty()) {
            elementsInStack1.add(stack1.pop());
        }

        while (!stack2.isEmpty()) {
            T element = stack2.pop();
            if (elementsInStack1.contains(element)) {
                commonElements.add(element);
            }
        }

        return commonElements;
    }

    public static void main(String[] args) {
        CustomStack<Integer> stack1 = new CustomStack<>(10);
        stack1.push(1);
        stack1.push(2);
        stack1.push(3);
        stack1.push(4);
        stack1.push(5);

        CustomStack<Integer> stack2 = new CustomStack<>(10);
        stack2.push(3);
        stack2.push(4);
        stack2.push(5);
        stack2.push(6);
        stack2.push(7);

        Set<Integer> commonElements = findCommonElements(stack1, stack2);

        System.out.println("Common elements between the two stacks: " + commonElements);
    }
}