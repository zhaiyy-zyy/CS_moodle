import java.util.ArrayList;
import java.util.List;


public class FibonacciCalculator {

    // ... (keep the binaryFib method as is)

    /**
     * Calculates and visualizes the Fibonacci sequence up to the kth number.
     *
     * @param k A nonnegative integer representing the position of the Fibonacci number to calculate
     */
    public static void visualizeFibonacci(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Input must be a nonnegative integer");
        }

        System.out.println("Calculating Fibonacci numbers up to F(" + k + "):");
        System.out.println("-----------------------------------");

        List<Long> fibNumbers = new ArrayList<>();
        fibNumbers.add(0L); // F0
        System.out.println("F(0) = 0");

        if (k == 0) {
            return;
        }

        fibNumbers.add(1L); // F1
        System.out.println("F(1) = 1");

        if (k == 1) {
            return;
        }

        for (int i = 2; i <= k; i++) {
            long nextFib = fibNumbers.get(i - 1) + fibNumbers.get(i - 2);
            fibNumbers.add(nextFib);
            System.out.printf("F(%d) = F(%d) + F(%d) = %d + %d = %d%n",
                    i, i - 1, i - 2, fibNumbers.get(i - 1), fibNumbers.get(i - 2), nextFib);
        }

        System.out.println("-----------------------------------");
        System.out.println("The " + k + "th Fibonacci number is: " + fibNumbers.get(k));
    }

    public static void main(String[] args) {
        int k = 10;
        visualizeFibonacci(k);
    }
}