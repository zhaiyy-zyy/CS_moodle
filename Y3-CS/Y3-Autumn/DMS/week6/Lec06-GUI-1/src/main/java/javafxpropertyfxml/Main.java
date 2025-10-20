package javafxpropertyfxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * IMPORTANT NOTE:
 * The source code is dedicated to demonstrate the MVC pattern, properties and binding,
 * but be aware that design patterns and principles are not applied here.
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("JavaFX Sample Code with Properties and Binding");
        Scene scene = new Scene(root, 500, 250, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
