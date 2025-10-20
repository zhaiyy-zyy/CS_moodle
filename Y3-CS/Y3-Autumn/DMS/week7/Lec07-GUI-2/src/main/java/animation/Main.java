package animation;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle rectangle = new Rectangle(50, 50, 100, 50);
        Rectangle rectangle2 = new Rectangle(50, 200, 100, 50);
        rectangle2.setFill(Color.RED);

        /*************************************************************************************************************/
        // sample animation
        /*
        KeyValue keyValueX1 = new KeyValue(rectangle.xProperty(), 750, Interpolator.EASE_IN);
        KeyValue keyValueY1 = new KeyValue(rectangle.yProperty(), 200, Interpolator.EASE_OUT);
        //KeyValue keyValueOpacity = new KeyValue(rectangle.opacityProperty(), 0);
        KeyValue keyValueX2 = new KeyValue(rectangle2.xProperty(), 750, Interpolator.EASE_IN);
        KeyValue keyValueY2 = new KeyValue(rectangle2.yProperty(), 50, Interpolator.EASE_OUT);

        //KeyFrame keyFrame1 = new KeyFrame(Duration.millis(10000), keyValueOpacity, keyValueX1);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(10000), keyValueX1, keyValueY1);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(5000), keyValueX2, keyValueY2);

        Timeline timeline = new Timeline();
        //timeline.setCycleCount(3);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        //timeline.getKeyFrames().addAll(keyFrame1);
        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
        timeline.play();
        */
        /*************************************************************************************************************/

        /*************************************************************************************************************/
        // path transition animation
        ///*
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setFill(Color.ORANGE);

        Path path = new Path();
        path.getElements().add(new MoveTo(100,100));
        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(8000));
        pathTransition.setPath(path);
        pathTransition.setNode(rectangle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
        //*/
        /*************************************************************************************************************/

        Group root = new Group();
        root.getChildren().addAll(rectangle, rectangle2);

        primaryStage.setTitle("Lecture 08 - Animation");
        primaryStage.setScene(new Scene(root, 900, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
