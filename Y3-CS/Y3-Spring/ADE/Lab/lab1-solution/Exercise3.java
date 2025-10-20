package labsection;

import java.util.HashSet;

public class Exercise3 {
    private int[] numbers;

    public Exercise3(int[] numbers) {
        this.numbers = numbers;
    }

    public boolean allUnique() {
        if (numbers == null) {
            throw new IllegalArgumentException("Array is null");
        }

        HashSet<Integer> set = new HashSet<>();
        for (int num : numbers) {
            if (!set.add(num)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        Exercise3 exercise = new Exercise3(numbers);
        if (exercise.allUnique()) {
            System.out.println("All elements in the array are unique.");
        } else {
            System.out.println("There are duplicate elements in the array.");
        }
    }
}
