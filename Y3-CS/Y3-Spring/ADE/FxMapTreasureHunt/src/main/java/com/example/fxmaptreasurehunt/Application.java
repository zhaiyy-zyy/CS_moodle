package com.example.fxmaptreasurehunt;

import com.example.fxmaptreasurehunt.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Text welcomeText = new Text("Welcome to Treasure hunt game.\nThere are 3 treasure you need to find.");
        Button startGameButton = new Button("Start game");
        startGameButton.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("layout/game.fxml"));
            Parent parent= null;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(parent, 605, 650);
            GameController gameController=fxmlLoader.getController();
            gameController.setScene(scene);
            stage.setTitle("Treasure Hunt");
            stage.setScene(scene);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double x = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double y = (screenBounds.getHeight() - stage.getHeight()) / 2;
            stage.setX(x);
            stage.setY(y);
            stage.show();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            stage.close();
        });
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(welcomeText, startGameButton, cancelButton);
        vbox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane();
        root.getChildren().add(vbox);
        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}