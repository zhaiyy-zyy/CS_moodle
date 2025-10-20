package com.example.platformer.view;

import com.example.platformer.model.Game;
import com.example.platformer.model.Leaderboard;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Represents the view layer for the platformer game.
 * Provides methods to create and manage UI components such as score labels, time labels, pause buttons, and leaderboard dialogs.
 */
public class GameView {
    /**
     * Creates and returns a label displaying the player's current score.
     *
     * @param score The current score to display.
     * @return A {@link Label} configured to display the player's score.
     */
    public Label scoreLabel(int score) {
        Label label = new Label("Score: " + score);
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 16px;");
        label.setLayoutX(10);
        label.setLayoutY(10);
        return label;
    }
    /**
     * Creates and returns a label to display the game time.
     *
     * @return A {@link Label} initialized with "Time: 0s".
     */
    public Label timeLabel() {
        Label label = new Label("Time: 0s");
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 16px;");
        label.setLayoutX(10);
        label.setLayoutY(30);
        return label;
    }
    /**
     * Creates and returns a button to pause or resume the game.
     *
     * @return A {@link Button} initialized with the text "Pause".
     */
    public Button pauseButton() {
        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutX(1210);
        pauseButton.setLayoutY(15);
        return pauseButton;
    }
    /**
     * Creates and returns a label for displaying in-game messages.
     *
     * @return A {@link Label} configured for displaying messages such as game over or level completion.
     */
    public Label messageLabel() {
        Label messageLabel = new Label("");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        messageLabel.setLayoutX(640 - 150);
        messageLabel.setLayoutY(360 - 50);
        return messageLabel;
    }
    /**
     * Creates and returns a toggle button for enabling or disabling music.
     *
     * @return A {@link Button} initialized with the text "Music: ON".
     */
    public Button musicToggleButton() {
        Button musicToggleButton = new Button("Music: ON");
        musicToggleButton.setLayoutX(1200);
        musicToggleButton.setLayoutY(50);
        return musicToggleButton;
    }
    /**
     * Creates and returns the main game scene, configured with key press and release event handlers.
     *
     * @param appRoot The root {@link Pane} for the scene.
     * @param keys    A {@link HashMap} to track key press states.
     * @return A {@link Scene} configured with event handlers for key presses and releases.
     */
    public Scene createScene(Pane appRoot, HashMap<KeyCode, Boolean> keys) {
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        return scene;
    }

    /**
     * Displays a dialog window showing the leaderboard for the current game level.
     *
     * @param game The {@link Game} object containing leaderboard data.
     */
    public void showLeaderboardDialog(Game game) {
        Stage leaderboardStage = new Stage();
        Pane leaderboardPane = new Pane();
        leaderboardPane.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-border-color: black; -fx-border-width: 2;");

        Label leaderboardTitle = new Label("Leaderboard");
        leaderboardTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        leaderboardTitle.setLayoutX(150);
        leaderboardTitle.setLayoutY(10);

        // Get the leaderboard of the current level
        Leaderboard currentLeaderboard = game.leaderboards.get(game.currentLevel);
        String leaderboardText = currentLeaderboard != null ? currentLeaderboard.getFormattedScores() : "No scores available.";

        // Create a label for the leaderboard content
        Label leaderboardContent = new Label(leaderboardText);
        leaderboardContent.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        leaderboardContent.setWrapText(true);
        leaderboardContent.setMaxWidth(380);
        leaderboardContent.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        leaderboardContent.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);


        // ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(leaderboardContent);
        scrollPane.setLayoutX(20);
        scrollPane.setLayoutY(50);
        scrollPane.setPrefWidth(400);
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: lightgray;");

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-font-size: 14px; -fx-background-color: #FF5722; -fx-text-fill: white;");
        closeButton.setLayoutX(200);
        closeButton.setLayoutY(370);
        closeButton.setOnAction(event -> leaderboardStage.close());

        leaderboardPane.getChildren().addAll(leaderboardTitle, scrollPane, closeButton);

        Scene scene = new Scene(leaderboardPane, 450, 420);
        leaderboardStage.setScene(scene);
        leaderboardStage.setTitle("Leaderboard");
        leaderboardStage.show();
    }

}
