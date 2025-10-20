package labsection;

public class Exercise1 {

	private int[] numbers;

    public Exercise1(int[] numbers) {
        this.numbers = numbers;
    }

    public int findMax() {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array is null or empty");
        }
        
        int max = numbers[0];
        for (int num : numbers) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] numbers = {3, 5, 7, 2, 8, 6, 4};
        Exercise1 finder = new Exercise1(numbers);
        System.out.println("The maximum element is: " + finder.findMax());
    }
}
