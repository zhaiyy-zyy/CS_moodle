Method areAllDifferent(array of integers):
    For i from 0 to length of array - 1:
        For j from i + 1 to length of array:
            If array[i] == array[j]:
                Return false
    Return true


public class UniqueNumbers {
    public static boolean areAllDifferent(int[] array) {
        // Iterate through each pair of elements
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                // If any two elements are the same, return false
                if (array[i] == array[j]) {
                    return false;
                }
            }
        }
        // If no duplicates are found, return true
        return true;
    }

    public static void main(String[] args) {
        // Example usage
        int[] numbers = {1, 2, 3, 4, 5};
        if (areAllDifferent(numbers)) {
            System.out.println("All numbers are different.");
        } else {
            System.out.println("There are duplicates in the array.");
        }
    }
}