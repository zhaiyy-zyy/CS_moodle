package com.example.platformer.controller;

import com.example.platformer.model.Game;
import com.example.platformer.util.Util;
import com.example.platformer.view.InfoView;
import com.example.platformer.view.OptionsView;
import com.example.platformer.view.SelectLevelView;
import com.example.platformer.view.ShowLeaderboardView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller class for managing the main menu of the platformer game.
 * Provides functionality to navigate to different views such as game start, options, game information, level selection, and leaderboard, as well as exiting the application.
 */
public class MainMenuController {
    /**
     * The game model that holds the default game settings and properties.
     */
    public Game game = new Game(null);
    /**
     * Starts the game by initializing a new game scene and setting the background color.
     * Opens a new stage for the game interface.
     */
    @FXML
    public void Start() {
        GameController gameController = new GameController();
        Stage gameStage = new Stage();
        gameController.initGameScene(gameStage);
        gameController.setBackgroundColor(Util.backgroundColor);
    }
    /**
     * Opens the options menu, allowing the user to modify game settings such as background color.
     * Displays a color picker and a return button in a new stage.
     */
    @FXML
    public void Options() {
        OptionsView optionsView = new OptionsView();
        // Create a new Stage and Scene and load the new interface
        Stage stage = new Stage();

        Pane root = new Pane();
        ColorPicker colorPicker = optionsView.colorPicker(game);
        Button backButton = optionsView.backButton(stage);

        root.getChildren().addAll(colorPicker, backButton);
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Options");
        stage.show();
    }
    /**
     * Opens the game information menu, displaying information about the game and a return button.
     * The information menu is displayed in a new stage.
     */
    @FXML
    public void Info() {
        // Create a new Stage and Scene and load the new interface
        Stage stage = new Stage();
        Pane root = new Pane();

        InfoView infoView = new InfoView();
        root.getChildren().addAll(infoView.infoLabel(), infoView.backButton(stage));

        // Create the scene and display it
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Game Info");
        stage.show();
    }
    /**
     * Opens the level selection menu, allowing the user to select a level to play.
     * Each level is represented by a button, and a return button is also provided.
     * The level selection menu is displayed in a new stage.
     */
    @FXML
    public void SelectLevel() {
        // Create a level selection window
        Stage levelSelectionStage = new Stage();
        Pane root = new Pane();
        SelectLevelView selectLevelView = new SelectLevelView();
        root.setPrefSize(400, 300);

        // start the levels
        Button level1Button = selectLevelView.levelButton(1, levelSelectionStage, 150, 80);
        Button level2Button = selectLevelView.levelButton(2, levelSelectionStage, 150, 120);
        Button level3Button = selectLevelView.levelButton(3, levelSelectionStage, 150, 160);
        Button level4Button = selectLevelView.levelButton(4, levelSelectionStage, 150, 200);


        // return button
        Button closeButton = new Button("Return");
        closeButton.setLayoutX(150);
        closeButton.setLayoutY(240);
        closeButton.setOnAction(event -> levelSelectionStage.close());

        root.getChildren().addAll(selectLevelView.titleLabel(), level1Button, level2Button, level3Button, level4Button, closeButton);
        Scene scene = new Scene(root);
        levelSelectionStage.setScene(scene);
        levelSelectionStage.setTitle("Select Level");
        levelSelectionStage.show();
    }

    /**
     * Opens the leaderboard menu, allowing the user to view leaderboards for different levels.
     * Displays buttons for each level and a return button. Each level button opens a leaderboard file.
     * The leaderboard menu is displayed in a new stage.
     */
    @FXML
    public void ShowLeaderboard() {
        Stage leaderboardStage = new Stage();
        Pane root = new Pane();
        ShowLeaderboardView showLeaderboardView = new ShowLeaderboardView();
        root.setPrefSize(1280, 720);

        Button level1Button = showLeaderboardView.levelButton(1, 540, 200);
        Button level2Button = showLeaderboardView.levelButton(2, 540, 250);
        Button level3Button = showLeaderboardView.levelButton(3, 540, 300);
        Button level4Button = showLeaderboardView.levelButton(4, 540, 350);

        // A separate leaderboard window opens
        level1Button.setOnAction(event -> Util.openLeaderboardPage("./leaderboard_level_1.txt", "Level 1 Leaderboard"));
        level2Button.setOnAction(event -> Util.openLeaderboardPage("./leaderboard_level_2.txt", "Level 2 Leaderboard"));
        level3Button.setOnAction(event -> Util.openLeaderboardPage("./leaderboard_level_3.txt", "Level 3 Leaderboard"));
        level4Button.setOnAction(event -> Util.openLeaderboardPage("./leaderboard_level_4.txt", "Level 4 Leaderboard"));

        root.getChildren().addAll(showLeaderboardView.titleLabel(), level1Button, level2Button, level3Button, level4Button, showLeaderboardView.closeButton(leaderboardStage, "Return", 540, 450));

        // Create the scene and display it
        Scene scene = new Scene(root);
        leaderboardStage.setScene(scene);
        leaderboardStage.setTitle("Leaderboard");
        leaderboardStage.show();
    }
    /**
     * Exits the application by calling {@link System#exit(int)}.
     * Clicks "Exit" to the quit the game.
     */
    @FXML
    public void Exit() {
        System.out.println("Exit");
        System.exit(0);
    }

}
