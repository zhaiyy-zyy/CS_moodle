package javafxlayout;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("HelloJavaFXWorld");

        // drawing line
        Line line = new Line(0, 100, 100, 200);
        //Line line = new Line(100, 200, 0, 100);
        line.setStrokeWidth(5);
        Group root = new Group();
        root.getChildren().add(line);


        Button btn1 = new Button("One");
        Button btn2 = new Button("Two");
        Button btn3 = new Button("Three");
        Button btn4 = new Button("Four");
        Button btn5 = new Button("Five Five Five");

        /************************************************************************************************************/
        // using BorderPane
        BorderPane.setAlignment(btn1, Pos.CENTER);
        BorderPane.setAlignment(btn2, Pos.TOP_LEFT);
        BorderPane.setAlignment(btn3, Pos.TOP_LEFT);
        BorderPane.setAlignment(btn4, Pos.BOTTOM_RIGHT);
        BorderPane.setAlignment(btn5, Pos.CENTER_LEFT);
       // BorderPane root = new BorderPane(btn1, btn2, btn3, btn4, btn5);
        /************************************************************************************************************/

        /************************************************************************************************************/
        // using BorderPane with HBox and VBox
        /*HBox hBox = new HBox();
        VBox vBox = new VBox();
        hBox.getChildren().addAll(btn1, btn2);
        vBox.getChildren().addAll(btn3, btn4, btn5);

        // set the gap between nodes
        hBox.setSpacing(10);
        vBox.setSpacing(10);

        BorderPane root = new BorderPane();
        root.setLeft(hBox);
        root.setRight(vBox);*/
        /************************************************************************************************************/

        /************************************************************************************************************/
        // using StackPane
        /*StackPane root = new StackPane();
        Rectangle rect1 = new Rectangle(0, 0, 300, 300);
        rect1.setFill(Color.BLUE);
        Rectangle rect2 = new Rectangle(0, 0, 200, 200);
        rect2.setFill(Color.GREEN);
        root.getChildren().addAll(rect2, rect1);*/
        /************************************************************************************************************/

        /************************************************************************************************************/
        // using GridPane
        /*GridPane root = new GridPane();
        root.add(btn1, 0, 0);           // column 1, row 1
        root.add(btn2, 1, 2);           // column 2, row 3
        root.add(btn3, 3, 1);           // column 4, row 2
        root.add(btn4, 1, 0);           // column 2, row 1
        root.add(btn5, 2, 2);           // column 3, row 3

        root.setHgap(10);                   // set horizontal gap/spacing
        root.setVgap(20);                   // set vertical gap/spacing*/
        /************************************************************************************************************/

        /************************************************************************************************************/
        // using FlowPane
        /*FlowPane root = new FlowPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setHgap(5);
        root.setVgap(20);
        root.getChildren().addAll(btn1, btn2, btn3, btn4, btn5);*/
        /************************************************************************************************************/

        /************************************************************************************************************/
        // using TilePane
        /*TilePane root = new TilePane();
        root.setHgap(5);
        root.setVgap(20);
        root.getChildren().addAll(btn1, btn2, btn3, btn4, btn5);*/
        /************************************************************************************************************/

        /************************************************************************************************************/
        // using AnchorPane with HBox and VBox
        /*HBox hBox = new HBox();
        VBox vBox = new VBox();
        hBox.getChildren().addAll(btn1, btn2);
        vBox.getChildren().addAll(btn3, btn4, btn5);

        // set the gap between nodes
        hBox.setSpacing(10);
        vBox.setSpacing(10);

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(hBox, vBox);
        AnchorPane.setLeftAnchor(hBox, 0.0);
        AnchorPane.setRightAnchor(vBox, 110.0);*/
        /*BorderPane root = new BorderPane();
        root.setLeft(hBox);
        root.setRight(vBox);*/
        /************************************************************************************************************/

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
