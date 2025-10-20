package main;

public class BubbleSort {
    /** Bubble-sort of an array of characters into non-decreasing order */
    public static void bubbleSort(char[] data) {
        int n = data.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false; // 设定标志位，优化排序

            for (int j = 0; j < n - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    // 交换 data[j] 和 data[j+1]
                    char temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;

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