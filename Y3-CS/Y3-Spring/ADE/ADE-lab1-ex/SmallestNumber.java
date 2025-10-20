import java.util.Scanner;

public class SmallestNumber {
    
    public static double findSmallest(double num1, double num2, double num3) {
        // Find the smallest of the three numbers
        if (num1 <= num2 && num1 <= num3) {
            return num1;
        } else if (num2 <= num1 && num2 <= num3) {
            return num2;
        } else {
            return num3;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the three numbers
        System.out.print("Input the first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Input the second number: ");
        double num2 = scanner.nextDouble();
        System.out.print("Input the third number: ");
        double num3 = scanner.nextDouble();

        // Find the smallest number
        double smallest = findSmallest(num1, num2, num3);

        // Output the result
        System.out.println("The smallest value is " + smallest);
    }
}