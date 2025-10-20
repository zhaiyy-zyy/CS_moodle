package main;

public class BubbleSort {
    /** Bubble-sort of an array of characters into non-decreasing order */
    public static void bubbleSort(char[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (data[i] > data[j]) {
                    data[i] += data[j];
                    data[j] = (char) (data[i] - data[j]);
                    data[i] -= data[j];
                }
            }
        }
    }

    public static void main(String[] args) {
        char[] a = {'C', 'E', 'B', 'D', 'A', 'I', 'J', 'L', 'K', 'H', 'G', 'F'};
        System.out.println(java.util.Arrays.toString(a));
        bubbleSort(a);
        System.out.println(java.util.Arrays.toString(a));
    }
}
