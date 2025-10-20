package com.example.platformer.model;

import com.example.platformer.view.ShowLeaderboardView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a leaderboard for the platformer game.
 * Manages player scores, saves and loads leaderboard data from a file, and display and format leaderboard content.
 */
public class Leaderboard {
    /**
     * A list of player scores managed by the leaderboard.
     */
    private List<PlayerScore> scores = new ArrayList<>();
    /**
     * The file path where the leaderboard data is saved and loaded.
     */
    private final String saveFile;
    /**
     * Constructs a {@code Leaderboard} object and initializes it by loading scores from the specified file.
     *
     * @param saveFile The file path for saving and loading leaderboard data.
     */
    public Leaderboard(String saveFile) {
        this.saveFile = saveFile;
        loadScores(); // The corresponding file is loaded during initialization
    }

    /**
     * Adds a player's score to the leaderboard and updates the leaderboard by sorting the scores.
     * Scores are sorted in descending order, and if scores are equal, winning records are sorted by elapsed time in ascending order.
     *
     * @param playerName  The name of the player.
     * @param score       The player's score.
     * @param isWin       {@code true} if the player completed the level; {@code false} otherwise.
     * @param elapsedTime The time taken by the player to complete the level, in seconds.
     */
    public void addScore(String playerName, int score, boolean isWin, long elapsedTime) {
        scores.add(new PlayerScore(playerName, score, isWin, elapsedTime));
        // Sorting rules: First in descending order of scores, if the scores are the same, then in ascending order of completion time (only winning records compare time)
        scores.sort(Comparator.comparingInt((PlayerScore ps) -> -ps.getScore())
                .thenComparingLong(ps -> ps.isWin() ? ps.getElapsedTime() : Long.MAX_VALUE));
        saveScores(); //Save leaderboard data
    }

    /**
     * Displays the leaderboard in a new stage, showing the ranked list of scores.
     * The leaderboard includes player names, scores, and either completion times or "Not Completed" statuses.
     *
     * @param stage The {@link Stage} where the leaderboard is displayed.
     */
    public void showLeaderboard(Stage stage) {
        Pane leaderboardPane = new Pane();
        ShowLeaderboardView showLeaderboardView = new ShowLeaderboardView();

        StringBuilder leaderboardContent = new StringBuilder();
        int rank = 1;
        for (PlayerScore ps : scores) {
            leaderboardContent.append(rank++)
                    .append(". ")
                    .append(ps.getPlayerName())
                    .append(" - Score: ")
                    .append(ps.getScore())
                    .append(ps.isWin() ? " (Completed in " + ps.getElapsedTime() + "s)" : " (Not Completed)")
                    .append("\n");
        }

        leaderboardPane.getChildren().addAll(showLeaderboardView.title(), showLeaderboardView.content(leaderboardContent), showLeaderboardView.closeButton(stage, "Close", 200, 300));

        Scene scene = new Scene(leaderboardPane, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Leaderboard");
        stage.show();
    }

    /**
     * Saves the current leaderboard data to the specified file.
     * Each score entry is saved in a comma-separated format.
     */
    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            for (PlayerScore ps : scores) {
                writer.write(ps.getPlayerName() + "," + ps.getScore() + "," + ps.isWin() + "," + ps.getElapsedTime());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save leaderboard: " + e.getMessage());
        }
    }
    /**
     * Returns the leaderboard data as a formatted string.
     * The string contains player rankings, names, scores, and either completion times or "Not Completed" statuses.
     *
     * @return A formatted string representing the leaderboard.
     */
    public String getFormattedScores() {
        StringBuilder formattedScores = new StringBuilder();
        int rank = 1;

        for (PlayerScore ps : scores) {
            formattedScores.append(rank++)
                    .append(". ")
                    .append(ps.getPlayerName())
                    .append(" - Score: ")
                    .append(ps.getScore())
                    .append(ps.isWin() ? " (Completed in " + ps.getElapsedTime() + "s)" : " (Not Completed)")
                    .append("\n");
        }

        return formattedScores.toString();
    }

    /**
     * Loads leaderboard data from the specified file.
     * If the file exists, player scores are loaded and sorted according to the leaderboard rules.
     */
    private void loadScores() {
        File file = new File(saveFile);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String playerName = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        boolean isWin = Boolean.parseBoolean(parts[2]);
                        long elapsedTime = Long.parseLong(parts[3]);
                        scores.add(new PlayerScore(playerName, score, isWin, elapsedTime));
                    }
                }
                // Sort the loaded leaderboards
                scores.sort(Comparator.comparingInt((PlayerScore ps) -> -ps.getScore())
                        .thenComparingLong(ps -> ps.isWin() ? ps.getElapsedTime() : Long.MAX_VALUE));
            } catch (IOException e) {
                System.err.println("Failed to load leaderboard: " + e.getMessage());
            }
        }
    }
}
