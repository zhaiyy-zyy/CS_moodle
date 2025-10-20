package com.example.platformer.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Represents the view for displaying the leaderboard page.
 * Provides methods to create UI components such as the title, leaderboard content, and a return button.
 */
public class OpenLeaderboardPageView {
    /**
     * Creates and returns a label for displaying the title of the leaderboard page.
     *
     * @param pageTitle The title of the leaderboard page.
     * @return A {@link Label} styled and positioned to display the leaderboard page title.
     */
    public Label titleLabel(String pageTitle) {
        // Title
        Label titleLabel = new Label(pageTitle);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setLayoutX(600);
        titleLabel.setLayoutY(20);
        return titleLabel;
    }
    /**
     * Creates and returns a label to display the leaderboard content.
     * The label is styled and configured to support multi-line wrapped text.
     *
     * @return A {@link Label} configured for displaying leaderboard data.
     */
    public Label leaderboardContent(){
        // Label that displays the leaderboard content
        Label leaderboardContent = new Label();
        leaderboardContent.setLayoutX(50);
        leaderboardContent.setLayoutY(80);
        leaderboardContent.setPrefWidth(500);
        leaderboardContent.setWrapText(true);
        leaderboardContent.setStyle("-fx-font-size: 14px;");
        return leaderboardContent;
    }
    /**
     * Creates and returns a button for returning to the previous screen or closing the leaderboard page.
     * When clicked, the button closes the provided leaderboard page window.
     *
     * @param leaderboardPage The {@link Stage} representing the leaderboard page window to be closed.
     * @return A {@link Button} configured as a return button.
     */
    public Button closeButton(Stage leaderboardPage) {
        // Return Button
        Button closeButton = new Button("Return");
        closeButton.setLayoutX(1220);
        closeButton.setLayoutY(20);
        closeButton.setOnAction(event -> leaderboardPage.close());
        return closeButton;
    }
}
