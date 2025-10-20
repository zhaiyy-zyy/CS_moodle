import java.util.Scanner;

public class EvenDigitsChecker {

    public static boolean areAllDigitsEven(int number) {
        // Convert the number to a positive number in case it is negative
        number = Math.abs(number);
        
        // Check each digit
        while (number > 0) {
            int digit = number % 10; // Extract the last digit
            if (digit % 2 != 0) { // If the digit is odd
                return false;
            }
            number = number / 10; // Remove the last digit
        }
        
        return true; // All digits are even
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the integer
        System.out.print("Input an integer: ");
        int inputNumber = scanner.nextInt();

        // Check if every digit of the integer is even and display the result
        boolean result = areAllDigitsEven(inputNumber);
        System.out.println("Check whether every digit of the said integer is even or not: " + result);
    }
}