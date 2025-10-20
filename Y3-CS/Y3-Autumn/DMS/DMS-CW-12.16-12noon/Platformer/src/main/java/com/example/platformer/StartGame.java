package com.example.platformer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The main entry point for the platformer game application.
 * Initializes the main menu and starts the JavaFX application.
 */
public class StartGame extends Application {
    /**
     * Starts the JavaFX application by loading the main menu from an FXML file.
     * Configures the primary stage with the main menu scene.
     *
     * @param primaryStage The primary {@link Stage} for the application.
     * @throws Exception If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/platformer/controller/MainMenu.fxml"));
        Pane root = loader.load();
        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * The main method for launching the application.
     * Calls the JavaFX `launch` method to start the application lifecycle.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
