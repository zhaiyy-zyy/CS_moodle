package com.example.fxmaptreasurehunt;

import com.example.fxmaptreasurehunt.controller.GameController;
import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * The main entry point for the Treasure Hunt game.
 * This class initializes the welcome screen, handles user interactions
 * for starting the game, viewing the top 5 ranking, and displaying game rules.
 */
public class Application extends javafx.application.Application {

    /**
     * The entry point for starting the game application.
     * Initializes the welcome screen with buttons to start the game, show help, view the top 5 rankings,
     * and close the application.
     *
     * @param stage the primary stage for the application
     * @throws IOException if there is an error loading the FXML for the game screen
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Welcome text, reminding players of the goal of the game
        Text welcomeText = new Text("Welcome to Treasure hunt game.\n There are 3 treasure you need to find.");

        // Start Button
        Button startGameButton = new Button("Start");

        // Info button to introduce the game rules
        Button helpButton = new Button("Info");
        helpButton.setOnAction(event -> {
            showMessage("Treasure Hunt Game Rules\n" +
                    "\n" +
                    "Objective:\n" +
                    "\n" +
                    "Find all three hidden treasures while avoiding obstacles.\n" +
                    "\n" +
                    "Gameplay Features:\n" +
                    "\t1.\tMap Generation:\n" +
                    "\t•\tA 20x20 grid with obstacles, treasures, and grassland. Obstacles block movement; treasures are the goal.\n" +
                    "\t2.\tPlayer Movement:\n" +
                    "\t•\tMove in four directions (up, down, left, right).\n" +
                    "\t•\tEach move costs 1 point. Hitting obstacles reduces the score by 10 points.\n" +
                    "\t3.\tTreasures:\n" +
                    "\t•\tCollect all three treasures to win. Each treasure will add 20 points.\n" +
                    "\t4.\tScore System:\n" +
                    "\t•\tStarts at 100 points.\n" +
                    "\t•\tPoints decrease with movement, obstacle collisions, and hint usage (3 points per hint).\n" +
                    "\t5.\tHints:\n" +
                    "\t•\tUse hints to reveal the shortest path to treasures, costing 3 points.\n" +
                    "\t6.\tMagic Bottle:\n" +
                    "\t•\tIf the score drops below 10, the player can use a magic bottle to regain 20 points. Only one use per game.\n" +
                    "\t7.\tGame Over:\n" +
                    "\t•\tGame ends when all treasures are collected or the score reaches 0.\n" +
                    "Ranking:\n" +
                    "\t•\tAfter the game, the player’s score and the top 5 scores are displayed.");
        });

        // Top five ranking button, click to display the top five player rankings
        Button top5Button = new Button("Top 5 Ranking");
        top5Button.setOnAction(event -> {
            showTop5Dialog();
        });

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            stage.close();
        });

        // Add all the buttons and welcome text to the layout
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(welcomeText, startGameButton, helpButton, top5Button, exitButton);
        vbox.setAlignment(Pos.CENTER); // Set the button to center

        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        root.setBackground(new Background(ResourceManagerUtil.getBackground(true)));

        // Create a scene and set the size to 300x200
        Scene scene = new Scene(root, 300, 200);

        stage.setTitle("Welcome");
        stage.setScene(scene);

        // Set the click event of the start game button and load the game scene after clicking it
        startGameButton.setOnAction(event -> {
            Stage stage1 = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("layout/game.fxml"));
            Parent parent = null;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene1 = new Scene(parent, 600, 625);
            GameController gameController = fxmlLoader.getController();
            gameController.setScene(scene1);
            gameController.setBackScene(scene, stage, stage1);
            stage1.setTitle("Treasure Hunt");
            stage1.setScene(scene1);
            stage1.show();
            stage.hide();
        });
        stage.show();
    }

    /**
     * Displays the top 5 player rankings in a modal dialog.
     * The top scores are loaded and displayed in a list with their corresponding treasure count.
     */
    public void showTop5Dialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Top 5");
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(5));

        // Display the title of the score and treasure number
        Label topScoresLabel = new Label("     score   treasure");
        dialogVBox.getChildren().add(topScoresLabel);

        // Get and display the top five scores and treasure counts
        List<int[]> topScores = ResourceManagerUtil.getTop5Score();
        for (int i = 0; i < topScores.size(); i++) {
            dialogVBox.getChildren().add(new Label((i + 1) + ".   " + String.format("%5d", topScores.get(i)[0]) + "   " + String.format("%8d", topScores.get(i)[1])));
        }

        // Create an "ok" button and close the dialog box after clicking it
        Button continueButton = new Button("ok");
        continueButton.setOnAction(event -> {
            dialog.close();
        });

        // Create the button layout and center it
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(continueButton);
        buttonBox.setAlignment(Pos.CENTER);
        dialogVBox.getChildren().add(buttonBox);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setBackground(new Background(ResourceManagerUtil.getBackground(true)));
        Scene dialogScene = new Scene(dialogVBox, 300, 250);
        dialog.setScene(dialogScene);

        dialog.showAndWait();
    }

    /**
     * Displays a message in a modal dialog.
     *
     * @param msg the message to be displayed in the dialog
     */
    public void showMessage(String msg) {
        Stage dialog = new Stage();
        dialog.setTitle("Message");
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        // Show message
        Label playerScoreLabel = new Label(msg);
        dialogVBox.getChildren().add(playerScoreLabel);
        dialogVBox.setAlignment(Pos.CENTER);

        // Create an "ok" button and close the dialog box after clicking it
        Button continueButton = new Button("ok");
        continueButton.setOnAction(event -> {
            dialog.close();
        });
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(continueButton);
        buttonBox.setAlignment(Pos.CENTER);
        dialogVBox.getChildren().add(buttonBox);
        dialogVBox.setBackground(new Background(ResourceManagerUtil.getBackground(false)));
        Scene dialogScene = new Scene(dialogVBox, 750, 600);
        dialog.setScene(dialogScene);

        dialog.showAndWait();
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}