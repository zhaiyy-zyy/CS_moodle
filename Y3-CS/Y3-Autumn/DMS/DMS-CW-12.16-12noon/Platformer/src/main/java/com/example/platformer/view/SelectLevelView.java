package com.example.platformer.view;

import com.example.platformer.controller.GameController;
import com.example.platformer.util.Util;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * Represents the view for selecting a game level.
 * Provides methods to create UI components for selecting and starting a specific level.
 */
public class SelectLevelView {
    /**
     * Creates and returns a label for the title of the level selection screen.
     *
     * @return A {@link Label} styled and positioned to display the title "Select Level".
     */
    public Label titleLabel() {
        // Title
        Label titleLabel = new Label("Select Level");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        titleLabel.setLayoutX(150);
        titleLabel.setLayoutY(20);
        return titleLabel;
    }
    /**
     * Creates and returns a button for selecting and starting a specific game level.
     * Clicking the button will initialize the selected level and close the level selection screen.
     *
     * @param level                 The level number to be started (1-based).
     * @param levelSelectionStage   The {@link Stage} representing the level selection screen to be closed after selecting a level.
     * @param x                     The X-coordinate for positioning the button.
     * @param y                     The Y-coordinate for positioning the button.
     * @return A {@link Button} configured for selecting the specified level.
     */
    public Button levelButton(int level, Stage levelSelectionStage, int x, int y) {
        String text = "Level " + level;
        Button levelButton = new Button(text);
        levelButton.setLayoutX(x);
        levelButton.setLayoutY(y);
        levelButton.setOnAction(event -> {
            startLevel(level);
            levelSelectionStage.close();
        });
        return levelButton;
    }
    /**
     * Starts the specified game level by initializing the game controller, setting the level,
     * and creating the game scene with the selected level.
     *
     * @param level The level number to start (1-based).
     */
    private void startLevel(int level) {
        GameController gameController = new GameController();
        // set level
        gameController.getLevelController().setLevel(level - 1);
        Stage gameStage = new Stage();
        // start game
        gameController.initGameScene(gameStage);
        // use background color
        gameController.setBackgroundColor(Util.backgroundColor);
    }
}
