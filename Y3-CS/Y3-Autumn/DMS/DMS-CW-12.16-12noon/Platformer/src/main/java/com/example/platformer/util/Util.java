package com.example.platformer.util;

import com.example.platformer.model.Leaderboard;
import com.example.platformer.view.OpenLeaderboardPageView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Utility class for handling common operations and configurations in the platformer game.
 * Provides methods and shared resources such as background color settings and leaderboard page management.
 */
public class Util {

    /**
     * The default background color for the game.
     */
    public static Color backgroundColor = Color.LIGHTBLUE;
    /**
     * Opens a leaderboard page in a new window.
     * Reads the leaderboard data from the specified file, formats it, and displays it in a new stage with a custom title.
     *
     * @param leaderboardFile The path to the file containing leaderboard data.
     * @param pageTitle       The title for the leaderboard page.
     */
    public static void openLeaderboardPage(String leaderboardFile, String pageTitle) {
        // Create new stages and Panes
        Stage leaderboardPage = new Stage();
        Pane root = new Pane();
        OpenLeaderboardPageView openLeaderboardPageView = new OpenLeaderboardPageView();
        root.setPrefSize(1280, 720);

        Label leaderboardContent = openLeaderboardPageView.leaderboardContent();

        // Load leaderboard data
        Leaderboard leaderboard = new Leaderboard(leaderboardFile);
        leaderboardContent.setText(leaderboard.getFormattedScores());

        root.getChildren().addAll(openLeaderboardPageView.titleLabel(pageTitle), leaderboardContent, openLeaderboardPageView.closeButton(leaderboardPage));

        Scene scene = new Scene(root);
        leaderboardPage.setScene(scene);
        leaderboardPage.setTitle(pageTitle);
        leaderboardPage.show();
    }
}
