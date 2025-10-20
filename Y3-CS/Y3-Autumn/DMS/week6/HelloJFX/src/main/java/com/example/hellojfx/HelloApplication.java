package com.example.hellojfx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        */
        BorderPane root = new BorderPane();
        Button btn = new Button("Close App");
        BorderPane.setAlignment(btn, Pos.CENTER);
        root.setTop(btn);

        TextField textFieldInput = new TextField();
        // textFieldInput
        Label labelOutput = new Label("Default Text");
        root.setBottom(textFieldInput);
        root.setCenter(labelOutput);

        textFieldInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                labelOutput.setText(newValue);
            }
        });

        // using event listener with lambda expression
        /*textFieldInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            labelOutput.setText(newValue);
        });*/

        textFieldInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("text field changed!");
            }
        });

        // event handler of button with lambda expression
        btn.setOnMouseClicked(event -> {
            System.out.println("Button pressed!");
        });

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}