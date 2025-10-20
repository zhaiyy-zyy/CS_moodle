import java.util.Scanner;

public class SumOfDigits {

    public static int sumOfDigits(int number) {
        int sum = 0;
        
        // Extract digits and calculate the sum
        while (number > 0) {
            sum += number % 10; // Add the last digit
            number = number / 10; // Remove the last digit
        }
        
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the integer
        System.out.print("Input an integer: ");
        int input = scanner.nextInt();

        // Calculate and display the sum of digits
        int result = sumOfDigits(input);
        System.out.println("The sum is " + result);
    }
}