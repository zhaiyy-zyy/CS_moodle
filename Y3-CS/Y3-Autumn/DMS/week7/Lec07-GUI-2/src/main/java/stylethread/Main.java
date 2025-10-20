package stylethread;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Lecture 07 - Styling, Control & Multi-Threading");

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //Scene scene = new Scene(root, 500, 500);

        /*************************************************************************************************************/
        // CSS Introduction
        /*
        Group root = new Group();
        Scene scene = new Scene(root, 300, 275);
        // scene.getStylesheets().add("styles.css");
        //scene.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);

        Button button = new Button("JavaFX");
        button.setStyle("-fx-background-color: green; -fx-font-size: 50;");
        root.getChildren().add(button);
        */
        /*************************************************************************************************************/

        /*************************************************************************************************************/
        // using FXML and CSS
        ///*
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // parent style sheet
        root.setStyle("-fx-font-size: 20;");

        Scene scene = new Scene(root, 500, 500);
        //scene.getStylesheets().add(String.valueOf(getClass().getResource("stylesgridpane.css")));
        //*/
        /*************************************************************************************************************/

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
