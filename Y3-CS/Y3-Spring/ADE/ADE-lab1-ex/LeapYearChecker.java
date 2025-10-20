import java.util.Scanner;

public class LeapYearChecker {

    public static boolean isLeapYear(int year) {
        // Check if the year is a leap year
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return true; // It's a leap year
        } else {
            return false; // It's not a leap year
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the year
        System.out.print("Input a year: ");
        int year = scanner.nextInt();

        // Check if the year is a leap year and display the result
        if (isLeapYear(year)) {
            System.out.println("true"); // Leap year
        } else {
            System.out.println("false"); // Not a leap year
        }
    }
}