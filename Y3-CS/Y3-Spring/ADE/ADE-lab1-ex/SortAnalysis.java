import java.util.Arrays;
import java.util.Random;

public class SortAnalysis {

    public static void main(String[] args) {
        // Test different array sizes
        int[] arraySizes = {1000, 5000, 10000, 20000, 50000, 100000}; // Array sizes to test
        Random rand = new Random();

        for (int size : arraySizes) {
            int[] array = new int[size];
            // Fill the array with random integers
            for (int i = 0; i < size; i++) {
                array[i] = rand.nextInt(1000); // Random integer between 0 and 1000
            }

            // Measure the time taken to sort the array
            long startTime = System.nanoTime();
            Arrays.sort(array);
            long endTime = System.nanoTime();

            // Calculate and print the time taken
            long duration = endTime - startTime;
            System.out.println("Array size: " + size + " - Time taken to sort: " + duration + " nanoseconds");
        }
    }
}