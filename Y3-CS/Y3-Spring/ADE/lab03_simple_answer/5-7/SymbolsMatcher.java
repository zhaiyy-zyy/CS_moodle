package ace;

import ace.util.LinkedStack;
import ace.util.Stack;

public class SymbolsMatcher {
    public static boolean test(String expression) {
        final String opening = "([{";
        final String closing = ")]}";

        Stack<Character> stack = new LinkedStack<>();

        for (char ch : expression.toCharArray()) {
            int idx = -1;

            if (opening.indexOf(ch) != -1) {
                stack.push(ch);
            } else if ((idx = closing.indexOf(ch)) != -1) {
                if (stack.isEmpty()) {
                    return false;
                }
                if (idx != opening.indexOf(stack.pop())) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
