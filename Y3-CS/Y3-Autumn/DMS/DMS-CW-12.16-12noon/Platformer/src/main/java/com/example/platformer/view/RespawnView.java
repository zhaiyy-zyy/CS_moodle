package com.example.platformer.view;

import com.example.platformer.State.ResumeState;
import com.example.platformer.model.Game;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/**
 * Represents the view for the respawn prompt in the platformer game.
 * Provides methods to create and display a pane for offering the player a choice to respawn after losing a life.
 */
public class RespawnView {
    /**
     * Creates and returns a styled pane for displaying the respawn prompt.
     * The pane includes a background image and is pre-configured with dimensions and layout.
     *
     * @return A {@link Pane} representing the respawn selection screen.
     */
    public Pane respawnPane() {
        // Create the respawn selection pane
        Pane respawnPane = new Pane();
        respawnPane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
        respawnPane.setPrefSize(400, 200);
        respawnPane.setLayoutX(440);
        respawnPane.setLayoutY(260);

        // Add background image
        ImageView background = new ImageView(new Image(getClass().getResource("/static/respawn.jpg").toExternalForm()));
        background.setFitWidth(400);  // Match the pane's width
        background.setFitHeight(200); // Match the pane's height
        background.setPreserveRatio(false); // Ensure it fills the whole pane

        // Add the background to the pane
        respawnPane.getChildren().add(background);
        return respawnPane;
    }
    /**
     * Creates and returns a label displaying the respawn prompt message.
     *
     * @return A {@link Label} styled with bold text asking the player if they want to respawn.
     */
    public Label messageLabel() {
        Label messageLabel = new Label("Use 10 points to respawn?");
        messageLabel.setTextFill(Color.BLUE);
        messageLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        messageLabel.setLayoutX(65);
        messageLabel.setLayoutY(80);
        return messageLabel;
    }
    /**
     * Creates and returns a button with the specified properties.
     *
     * @param text  The text to display on the button.
     * @param style The CSS style to apply to the button.
     * @param x     The X-coordinate for the button's position.
     * @param y     The Y-coordinate for the button's position.
     * @param width The preferred width of the button.
     * @return A {@link Button} styled and positioned according to the provided parameters.
     */
    public Button createButton(String text, String style, int x, int y, int width) {
        Button button = new Button(text);
        button.setStyle(style);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(width);
        return button;
    }
    /**
     * Displays the respawn pane, allowing the player to choose whether to respawn or end the game.
     * Adds "Yes" and "No" buttons to the pane, with appropriate event handlers for each choice.
     *
     * @param game The {@link Game} object representing the current game state.
     */
    public void respawnPane(Game game) {
        Pane respawnPane = respawnPane();

        Label messageLabel = messageLabel();

        // Yes button
        Button yesButton = createButton("Yes", "-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;", 100, 130, 80);

        // No button
        Button noButton = createButton("No", "-fx-font-size: 16px; -fx-background-color: #F44336; -fx-text-fill: white;", 220, 130, 80);


        // Yes button
        yesButton.setOnAction(event -> {
            // Deduct points and respawn
            game.score -= 10;
            game.notifyObservers(game.score);
            game.player.getNode().setTranslateX(0);
            game.player.getNode().setTranslateY(600);
            game.player.setVelocity(new Point2D(0, 0));
            game.player.setCanJump(true);
            game.player.setDead(false);
            game.gameRoot.setLayoutX(0);
            game.gameRoot.setLayoutY(0);
            // Ensure the player is facing right
            game.player.getNode().setScaleX(1); // ScaleX = 1 ensures the player faces right
            // resume the music
            game.gameController.getMusicPlayerController().playMusic();
            // resume game
            game.isPaused = false;
            game.startTime += System.currentTimeMillis() - game.pauseTime;

            game.gameController.setState(new ResumeState());
            // remove respawn pane
            game.uiRoot.getChildren().remove(respawnPane);
        });

        // No button
        noButton.setOnAction(event -> {
            // game over
            game.gameController.endGame(false);

            // remove respawn pane
            game.uiRoot.getChildren().remove(respawnPane);
        });

        respawnPane.getChildren().addAll(messageLabel, yesButton, noButton);

        game.uiRoot.getChildren().add(respawnPane);
    }
}
