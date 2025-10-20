package javafxapp;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HelloJavaFXWorld");

        Button btn = new Button("Close App");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Bye!");
                System.exit(0);
            }
        });

        // event handler of button with lambda expression
        /*btn.setOnAction(event -> {
            System.out.println("Bye!");
            System.exit(0);
        });*/

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        TextField textFieldInput = new TextField();
        TextField textFieldCopy = new TextField();
        Label labelOutput = new Label("Default Text");

        BorderPane root = new BorderPane();
        root.setTop(textFieldInput);
        root.setCenter(labelOutput);
        // root.setCenter(textFieldCopy);
        BorderPane.setAlignment(btn, Pos.CENTER);
        root.setBottom(btn);

        // using bind property
        // labelOutput.textProperty().bind(textFieldInput.textProperty());
        // textFieldCopy.textProperty().bindBidirectional(textFieldInput.textProperty());

        // using event listener
        /*textFieldInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                labelOutput.setText(newValue);
                // textFieldCopy.setText(newValue);
            }
        });*/

        // using event listener with lambda expression
        /*textFieldInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
                labelOutput.setText(newValue);
        });*/

        /*Slider slider = new Slider(0, 100, 0);
        root.setTop(slider);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                labelOutput.setText("Value is " + (int)slider.getValue());
            }
        });*/

        /*slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                labelOutput.setText("Value is " + (int)slider.getValue());
        });*/

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
}

