Method hasOddProduct(array of integers):
    For each element i in the array:
        For each element j after i in the array:
            If both i and j are odd:
                Return true
    Return false


public class OddProduct {
    public static boolean hasOddProduct(int[] array) {
        // Iterate over each pair of elements in the array
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                // Check if both elements are odd
                if (array[i] % 2 != 0 && array[j] % 2 != 0) {
                    return true; // Return true if the product is odd
                }
            }
        }
        return false; // No odd product found
    }

    public static void main(String[] args) {
        // Example usage
        int[] numbers = {2, 3, 4, 5};
        if (hasOddProduct(numbers)) {
            System.out.println("There is a pair with an odd product.");
        } else {
            System.out.println("No pair with an odd product.");
        }
    }
}