package ace;

import ace.util.*;

public class Exercise7 {
    public static String transformRPN(String expression) {
        Stack<String> ops = new LinkedStack<>();
        Stack<String> vals = new LinkedStack<>();

        for (char ch : expression.toCharArray()) {
            if (ch == '(')
                continue;

            else if ("+-*/^".indexOf(ch) != -1) {
                ops.push("" + ch);
            }

            else if (ch == ')') {
                String op = ops.pop();

                String v = vals.pop();

                String res = vals.pop() + v + op;

                vals.push(res);
            }

            else
                vals.push("" + ch);
        }

        return vals.pop();
    }
}
