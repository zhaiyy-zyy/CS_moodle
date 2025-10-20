package javafxbindingproperty;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("HelloJavaFXWorld");

        BorderPane root = new BorderPane();

        Label label = new Label();
        TextField textField1 = new TextField("1");
        TextField textField2 = new TextField("2");
        Button btn = new Button("Close App");


        // binding and property (bind vs bindDirectional)
        //textField2.textProperty().bindBidirectional(textField1.textProperty());

        // event handler (no lambda expressions)
        /*textField1.textProperty().addListener(new ChangeListener<String>() {
                 @Override
                 public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                     label.setText(newValue);
                 }
             }
        );*/

        // event handler (lambda expressions)
        /*textField1.textProperty().addListener((observableValue, oldValue, newValue) -> {
                label.setText(newValue);
            }
        );

        btn.setOnAction(actionEvent -> {

        });*/

        root.setCenter(textField2);
        root.setTop(textField1);

        Slider slider = new Slider(0, 100, 0);
        root.setBottom(slider);

        primaryStage.setScene(new Scene(root, 400, 100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
