package com.example.platformer.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * Represents the view for displaying game information and rules.
 * Provides methods for creating an informational label and a return button.
 */
public class InfoView {
    /**
     * Creates and returns a label containing the game rules and instructions.
     * The label includes details on objectives, controls, mechanics, and scoring.
     *
     * @return A {@link Label} displaying the game information, styled and formatted for readability.
     */
    public Label infoLabel() {
        // Create information Label
        String rules = "Game Rules:\n\n" +
                "Objective:\n" +
                "- Jump between platforms, collect coins, avoid enemies, and reach the finish line.\n\n" +
                "Player Controls:\n" +
                "- W: Jump\n" +
                "- A: Move left\n" +
                "- D: Move right\n" +
                "- Shift + A/D: Accelerated movement\n" +
                "- Pause/Resume: Use the Pause/Resume button.\n" +
                "- Music: Toggle ON/OFF.\n\n" +
                "Game Mechanics:\n" +
                "- Coins: +10 points each.\n" +
                "- Respawn: -10 points.\n" +
                "- Enemies: Collision results in respawn window.\n" +
                "- Finish Line: Complete the level to advance.\n" +
                "- Leaderboard: View rankings after each round.\n" +
                "- Timer: Tracks play time per level.\n\n" +
                "Good luck and have fun!";

        Label infoLabel = new Label(rules);
        infoLabel.setFont(new Font("Arial", 18));
        infoLabel.setLayoutX(10);
        infoLabel.setLayoutY(30);
        infoLabel.setWrapText(true);
        infoLabel.setMaxWidth(700);
        return infoLabel;
    }
    /**
     * Creates and returns a button for returning to the main menu or previous screen.
     * The button closes the current stage when clicked.
     *
     * @param stage The {@link Stage} representing the current window to be closed.
     * @return A {@link Button} configured as a return button.
     */
    public Button backButton(Stage stage) {
        // Return Button
        Button backButton = new Button("Return");
        backButton.setLayoutX(1200);
        backButton.setLayoutY(30);
        backButton.setOnAction(event -> {
            // Close the current window and return to the main window
            stage.close();
        });
        return backButton;
    }
}
