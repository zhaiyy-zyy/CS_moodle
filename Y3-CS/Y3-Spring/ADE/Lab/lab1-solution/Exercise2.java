package labsection;

public class Exercise2 {
    private int[] numbers;

    public Exercise2(int[] numbers) {
        this.numbers = numbers;
    }

    public boolean hasOddProductPair() {
        if (numbers == null || numbers.length < 2) {
            return false;
        }

        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if ((numbers[i] * numbers[j]) % 2 != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] numbers = {2, 4, 6, 3, 5};
        Exercise2 exercise = new Exercise2(numbers);
        if (exercise.hasOddProductPair()) {
            System.out.println("The array contains a pair of elements with an odd product.");
        } else {
            System.out.println("The array does not contain a pair of elements with an odd product.");
        }
    }
}
