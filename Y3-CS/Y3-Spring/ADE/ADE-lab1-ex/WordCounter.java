import java.util.Scanner;

public class WordCounter {

    public static int countWords(String str) {
        // Split the string into words based on spaces and count the length of the resulting array
        String[] words = str.trim().split("\\s+");
        return words.length; // Return the number of words
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the string
        System.out.print("Input the string: ");
        String inputString = scanner.nextLine();

        // Call countWords method and display the result
        int wordCount = countWords(inputString);
        System.out.println("Number of words in the string: " + wordCount);
    }
}