package multiplewindows;

import javafx.event.ActionEvent;

import java.io.IOException;

public class PrimaryController {

    public void switchOnClicked(ActionEvent actionEvent) throws IOException {
        Main.setRoot("secondary");
    }

}
