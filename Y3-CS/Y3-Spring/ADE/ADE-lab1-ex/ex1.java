Method FindMax(array of integers):
    Initialize max as the first element of the array
    For each element in the array:
        If element > max:
            Set max to element
    Return Max



public class MaxFinder{
    public static int findMax(int[] array){
        int max = array[0];
        for (int i = 1; i < array.length; i++){
            if (array[i] > max){
                max = array[i];
            }
        }
        return max;
    }
     public static void main(String[] args) {
        // Example usage
        int[] numbers = {1, 5, 3, 9, 2};
        System.out.println("The maximum element is: " + findMax(numbers));
    }
}