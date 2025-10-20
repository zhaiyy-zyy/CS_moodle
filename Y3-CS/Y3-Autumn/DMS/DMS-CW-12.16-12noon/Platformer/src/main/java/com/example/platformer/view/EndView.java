package com.example.platformer.view;

import com.example.platformer.controller.GameController;
import com.example.platformer.model.Game;
import com.example.platformer.model.Leaderboard;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the end game view in the platformer game.
 * Handles the display of the end game screen, including the final score, elapsed time, player name submission, and options for the next actions (e.g., restarting the game, proceeding to the next level, or exiting).
 */
public class EndView {
    /**
     * Creates and displays the end game pane with relevant information and options.
     * Includes the player's score, elapsed time, name entry for the leaderboard, and buttons for next actions.
     *
     * @param isWin       Indicates whether the player has won the game.
     * @param game        The {@link Game} object containing game state information.
     * @param elapsedTime The total time elapsed during the game session, in seconds.
     * @return A map of button identifiers to the corresponding {@link Button} objects for setting up event handlers.
     */
    public Map<String, Button> endGamePane(boolean isWin, Game game, long elapsedTime) {
        HashMap<String, Button> buttonHashMap = new HashMap<>();
        // The end information page is created
        Pane endGamePane = new Pane();
        endGamePane.setId("endViewPane");
        endGamePane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Determine if the game has another level
        boolean hasNextLevel = game.currentLevel < game.levels.length - 1;

        // Set the title and background image
        String message;
        String backgroundImagePath;
        double messageLayoutX;
        double messageLayoutY;

        if (isWin) {
            if (hasNextLevel) {
                message = "Congratulations!";
                messageLayoutX = 110;
                messageLayoutY = 10;
            } else {
                message = "Congratulations, completed all levels!";
                messageLayoutX = 10;
                messageLayoutY = 10;
            }
            backgroundImagePath = "file:src/main/resources/static/win.jpg";
        } else {
            message = "Game Over";
            messageLayoutX = 140;
            messageLayoutY = 10;
            backgroundImagePath = "file:src/main/resources/static/over.jpg";
        }

        ImageView backgroundImage = new ImageView(new Image(backgroundImagePath));
        backgroundImage.setFitWidth(400);
        backgroundImage.setFitHeight(300);
        backgroundImage.setOpacity(0.8);

        endGamePane.setPrefSize(400, 300);
        endGamePane.setLayoutX(440);
        endGamePane.setLayoutY(110);

        // Title: Win or Fail
        Label titleLabel = new Label(message);
        titleLabel.setTextFill(isWin ? Color.GREEN : Color.RED);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        titleLabel.setLayoutX(messageLayoutX);
        titleLabel.setLayoutY(messageLayoutY);

        // Score information
        Label infoLabel = new Label("Score: " + game.score + "\n");
        infoLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        infoLabel.setLayoutX(50);
        infoLabel.setLayoutY(110);

        // Time Information
        Label timeLabel = new Label("Time: " + elapsedTime + "s");
        timeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        timeLabel.setLayoutX(50);
        timeLabel.setLayoutY(140);

        // Name entry section
        Label nameLabel = new Label("Enter your name:");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333; -fx-font-weight: bold;");
        nameLabel.setLayoutX(50);
        nameLabel.setLayoutY(170);

        TextField nameField = new TextField();
        nameField.setLayoutX(50);
        nameField.setLayoutY(190);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setLayoutX(250);
        submitButton.setLayoutY(190);
        submitButton.setOnAction(event -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                Leaderboard currentLeaderboard = game.leaderboards.get(game.currentLevel);
                if (currentLeaderboard != null) {
                    currentLeaderboard.addScore(playerName, game.score, isWin, isWin ? elapsedTime : 0);
                }
                nameField.setDisable(true);
                submitButton.setDisable(true);
                nameLabel.setText("Name Submitted!");
            }
        });

        // Add leaderboard button
        Button leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        leaderboardButton.setLayoutX(50);
        leaderboardButton.setLayoutY(225);
        buttonHashMap.put("leaderboardButton", leaderboardButton);


        if (isWin && hasNextLevel) {
            Button nextButton = new Button("Next Level");
            nextButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
            nextButton.setLayoutX(250);
            nextButton.setLayoutY(225);
            endGamePane.getChildren().add(nextButton);
            buttonHashMap.put("nextButton", nextButton);
        }

        // Restart button
        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        restartButton.setLayoutX(50);
        restartButton.setLayoutY(260);
        buttonHashMap.put("restartButton", restartButton);

        // exit button
        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 16px; -fx-background-color: #F44336; -fx-text-fill: white;");
        exitButton.setLayoutX(250);
        exitButton.setLayoutY(260);
        buttonHashMap.put("exitButton", exitButton);

        // Adds a background image to the interface
        endGamePane.getChildren().add(0, backgroundImage);

        endGamePane.getChildren().addAll(titleLabel, infoLabel, timeLabel, nameLabel, nameField, submitButton, leaderboardButton, restartButton, exitButton);

        game.uiRoot.getChildren().add(endGamePane);

        return buttonHashMap;
    }
    /**
     * Sets event handlers for the buttons in the end game pane.
     * Links button actions to corresponding game controller methods, such as showing the leaderboard, restarting the game, proceeding to the next level, or exiting the game.
     *
     * @param endGamePane   A map of button identifiers to the corresponding {@link Button} objects.
     * @param gameController The {@link GameController} that manages the game logic and state transitions.
     */
    public void setEvent(Map<String, Button> endGamePane, GameController gameController) {
        Button leaderboardButton = endGamePane.get("leaderboardButton");
        leaderboardButton.setOnAction(event -> gameController.gameView.showLeaderboardDialog(gameController.game));

        Button nextButton = endGamePane.get("nextButton");
        if(nextButton != null) {
            nextButton.setOnAction(event -> gameController.getLevelController().nextLevel());
        }

        Button restartButton = endGamePane.get("restartButton");
        restartButton.setOnAction(event -> gameController.restartGame());

        Button exitButton = endGamePane.get("exitButton");
        exitButton.setOnAction(event -> Platform.exit());
    }

}
