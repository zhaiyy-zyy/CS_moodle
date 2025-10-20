package javafxfxml;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    //@FXML Button buttonClick;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button buttonClick = new Button("Close Application");
        buttonClick.setOnAction(event -> {
            System.out.println("Close!");
        });

        BorderPane root = new BorderPane();
        root.setCenter(buttonClick);

        buttonClick.setOnAction(event -> {
            System.out.println("Close!");
        });

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Lecture 08 - FXML");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
