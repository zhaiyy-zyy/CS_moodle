import java.util.Scanner;

public class MiddleCharacter {

    public static void findMiddleCharacter(String str) {
        int length = str.length();

        // Check if the length of the string is odd or even
        if (length % 2 != 0) {
            // If the length is odd, display the middle character
            int mid = length / 2;
            System.out.println("The middle character in the string: " + str.charAt(mid));
        } else {
            // If the length is even, display the two middle characters
            int mid1 = length / 2 - 1;
            int mid2 = length / 2;
            System.out.println("The middle characters in the string are: " 
                               + str.charAt(mid1) + " and " + str.charAt(mid2));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the string
        System.out.print("Input a string: ");
        String inputString = scanner.nextLine();

        // Find and display the middle character(s)
        findMiddleCharacter(inputString);
    }
}