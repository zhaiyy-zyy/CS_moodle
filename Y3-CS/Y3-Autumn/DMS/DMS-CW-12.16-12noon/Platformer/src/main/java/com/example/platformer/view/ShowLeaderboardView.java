package com.example.platformer.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Represents the view for displaying the leaderboard.
 * Provides methods for creating UI components such as titles, leaderboard content, level buttons, and close buttons.
 */
public class ShowLeaderboardView {
    /**
     * Creates and returns a label for the main title of the leaderboard selection screen.
     *
     * @return A {@link Label} styled and positioned to display the text "Select Level to View Leaderboard".
     */
    public Label titleLabel() {
        // Title
        Label titleLabel = new Label("Select Level to View Leaderboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        titleLabel.setLayoutX(450);
        titleLabel.setLayoutY(100);
        return titleLabel;
    }
    /**
     * Creates and returns a label for the leaderboard page title.
     *
     * @return A {@link Label} styled and positioned to display the text "Leaderboard".
     */
    public Label title() {
        Label title = new Label("Leaderboard");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        title.setLayoutX(50);
        title.setLayoutY(20);
        return title;
    }
    /**
     * Creates and returns a label to display the leaderboard content.
     *
     * @param leaderboardContent A {@link StringBuilder} containing the formatted leaderboard data.
     * @return A {@link Label} styled and positioned to display the leaderboard content.
     */
    public Label content(StringBuilder leaderboardContent) {
        Label content = new Label(leaderboardContent.toString());
        content.setLayoutX(50);
        content.setLayoutY(60);
        return content;
    }
    /**
     * Creates and returns a button for selecting a specific leaderboard by level.
     *
     * @param level The level number to display the leaderboard for.
     * @param x     The X-coordinate for positioning the button.
     * @param y     The Y-coordinate for positioning the button.
     * @return A {@link Button} configured with the level number text and positioned at the specified coordinates.
     */
    public Button levelButton(int level, int x, int y) {
        String text = "Level " + level;
        Button level1Button = new Button(text);
        level1Button.setLayoutX(x);
        level1Button.setLayoutY(y);
        return level1Button;
    }
    /**
     * Creates and returns a button for closing the leaderboard window.
     *
     * @param stage The {@link Stage} representing the leaderboard window to be closed.
     * @param text  The text to display on the button.
     * @param x     The X-coordinate for positioning the button.
     * @param y     The Y-coordinate for positioning the button.
     * @return A {@link Button} configured to close the provided stage when clicked.
     */
    public Button closeButton(Stage stage, String text, int x, int y) {
        Button closeButton = new Button(text);
        closeButton.setLayoutX(x);
        closeButton.setLayoutY(y);
        closeButton.setOnAction(event -> stage.close());
        return closeButton;
    }
}
