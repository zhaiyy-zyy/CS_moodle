package javafxfxml;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Controller {

    public void mouseOnClicked(MouseEvent mouseEvent) {
        System.out.println("Mouse Clicked!");
    }

    public void mouseOnEntered(MouseEvent mouseEvent) {
        System.out.println("Mouse Entered!");
    }

    public void mouseOnExited(MouseEvent mouseEvent) {
        System.out.println("Mouse Exited!");
    }

    public void onClicked(ActionEvent actionEvent) {
        System.out.println("Close App");
    }
}

