package labsection;


import java.util.Arrays;
import java.util.Random;

public class Exercise10 {
    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 50000, 100000, 200000, 500000};
        long[] times = new long[sizes.length];
        Random random = new Random();

        System.out.printf("%-10s %-15s%n", "Array Size", "Time (ms)");
        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) {
                arr[j] = random.nextInt(1000000);
            }

            long startTime = System.nanoTime();
            Arrays.sort(arr);
            long endTime = System.nanoTime();

            times[i] = (endTime - startTime) / 1_000_000; // Convert to milliseconds
            System.out.printf("%-10d %-15d%n", n, times[i]);
        }
    }
}
