package com.example.samplemvc;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HelloController {

    @FXML private Label labelOut;
    private Person person;

    public void onClickedOlder(MouseEvent mouseEvent) {
        person.incrementAge();
    }

    @FXML
    public void initialize() {
        person = new Person(20);
        // add a listener
        person.ageProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable,
                                        Number oldValue, Number newValue) {
                        labelOut.setText(newValue.toString());
                    }
                }
        );
    }
}
