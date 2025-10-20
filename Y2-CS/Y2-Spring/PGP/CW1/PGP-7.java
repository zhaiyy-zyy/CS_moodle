import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PGP {
    //Stores a list of algorithm results
    static List<AlgorithmResult> algorithmResults = new ArrayList<>();
    
    //Defines an internal AlgorithmResult class that stores the results of a single algorithm
    static class AlgorithmResult {
        int iteration;
        int phase;
        String name; //Algorithm name
        int elapsedTime = 0;
        int evaluations = 0;
        int improvements = 0;
        
        //Constructor for AlgorithmResult
        AlgorithmResult(int iteration, int phase, String name) {
            this.iteration = iteration;
            this.phase = phase;
            this.name = name;
        }
    }
    
    // Method to find or create an AlgorithmResult object
    private static AlgorithmResult findOrCreateAlgorithmResult(String name, int iteration, int phase) 
    {
        //Iterate through the existing list of results
        for (AlgorithmResult result : algorithmResults) 
        {
            // Check if the result with the same name, iteration, and phase already exists
            if (result.name.equals(name) && result.iteration == iteration && result.phase == phase) {
                return result; // Return the existing result
            }
        }
        // If the result does not exist, create a new one and add it to the list
        AlgorithmResult newResult = new AlgorithmResult(iteration, phase, name);
        algorithmResults.add(newResult);
        return newResult;
    }
    
    //Main function
    public static void main(String[] args) 
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Declare BufferedReader
        
        try
        {
            int iteration = 0;
            int phase = 0;
            boolean phaseFound = false; // Flag to indicate if phase is found in current iteration
        
            // Define regex patterns for iteration and phase
            Pattern iterationPattern = Pattern.compile("Iteration\\s+(\\d+)");
            Pattern phasePattern = Pattern.compile("Phase\\s+(\\d+)");

            // Create Matcher objects for iteration and phase patterns
            Matcher iterationMatcher = iterationPattern.matcher("");
            Matcher phaseMatcher = phasePattern.matcher("");
        
            // Iterate through each line of input
            String line;
            while ((line = reader.readLine()) != null) 
            {
                

                // Check if the line matches the iteration pattern
                iterationMatcher.reset(line);
                if (iterationMatcher.find()) 
                {
                    iteration = Integer.parseInt(iterationMatcher.group(1));
                    phaseFound = false; // Reset phaseFound flag when a new iteration begins
                    continue; // Continue to the next line
                }
            
                // Check if the line matches the phase pattern
                phaseMatcher.reset(line);
                if (phaseMatcher.find()) 
                {
                    phase = Integer.parseInt(phaseMatcher.group(1));
                    phaseFound = true; // Set flag to true when phase is found
                    continue; // Continue to the next line
                }

                // If phase is not found in current iteration, set it to 0
                if (!phaseFound) 
                {
                    phase = 0;
                }

                // Check if the line contains algorithm result data
                if (line.toLowerCase().contains("ends") && line.startsWith("-")) 
                {
                    String name = line.substring(2, line.toLowerCase().indexOf("ends")).trim();
                    // Removing any leading "-" and trimming the name
                    name = name.replace("-", "").trim();
                    // Find or create the AlgorithmResult object and update its data
                    AlgorithmResult result = findOrCreateAlgorithmResult(name, iteration, phase);
                
                    // Parse and update elapsed time, evaluations, and improvements
                    if (line.contains("Elapsed Time (ms):")) 
                    {
                        result.elapsedTime += Integer.parseInt(line.split("Elapsed Time \\(ms\\):")[1].trim().split(" ")[0]);
                    }
                    if (line.contains("Evaluations:")) 
                    {
                        result.evaluations += Integer.parseInt(line.split("Evaluations:")[1].trim().split(" ")[0]);
                    }
                    if (line.contains("Improvements:")) 
                    {
                        result.improvements += Integer.parseInt(line.split("Improvements:")[1].trim().split(" ")[0]);
                    }
                }
            }

            // Print individual algorithm results and calculate totals
            System.out.println("Iteration,Phase,Algorithm,Elapsed Time (ms),Evaluations,Improvements");
            int totalElapsedTime = 0, totalEvaluations = 0, totalImprovements = 0;
            for (AlgorithmResult result : algorithmResults) 
            {
                System.out.println(result.iteration + "," + result.phase + "," + result.name + "," + result.elapsedTime + "," + result.evaluations + "," + result.improvements);
                totalElapsedTime += result.elapsedTime;
                totalEvaluations += result.evaluations;
                totalImprovements += result.improvements;
            }

            // Print the total
            System.out.println("-1,-1,All," + totalElapsedTime + "," + totalEvaluations + "," + totalImprovements);
        }
        catch (IOException e) 
        {
            e.printStackTrace(); // Print the stack trace of the exception for debugging purposes
        }
    }
}