package stylethread;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Controller {

    @FXML private CheckBox checkBoxYes;
    @FXML private CheckBox checkBoxNo;
    @FXML private ComboBox<String> comboBox;
    @FXML private Label label;

    @FXML private ImageView imageView;
    private Rectangle rect;

    // Constructor
    public Controller() {
        System.out.println("Call constructor.");
        //checkBoxYes.setSelected(true);
    }
    /*
    @FXML
    private void initialize() {
        checkBoxYes.setSelected(true);

        ObservableList<String> comboBoxOptions =
                FXCollections.observableArrayList("Full", "Half Full", "Empty");
        comboBox.setItems(comboBoxOptions);

        label.textProperty().bind(comboBox.getSelectionModel().selectedItemProperty());

        Image image = new Image(String.valueOf(getClass().getResource("javafx.png")));
        imageView.setImage(image);

        comboBox.setOnAction(event -> {
            //String s = (String) comboBox.getSelectionModel().getSelectedItem();
            int index = comboBox.getSelectionModel().getSelectedIndex();
            System.out.println("Selected choice is \"" + index + "\"");
            label.setText(String.valueOf(index));
        });
    }
    */
    public void comboBoxOnItemSelected(ActionEvent actionEvent) {
        String s = comboBox.getSelectionModel().getSelectedItem();
        System.out.println("Selected choice is \"" + s + "\"");
        label.setText(s);
    }

    public void checkBoxYesOnClicked(ActionEvent actionEvent) {
        checkBoxNo.setSelected(!checkBoxYes.isSelected());
    }

    public void menuItemCloseClicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML private GridPane gridPane;
    @FXML
    private void initialize() {
        checkBoxYes.setSelected(true);

        // parent style for checkBoxYes
        //gridPane.setStyle("-fx-border-width: 10; -fx-border-color: orange;");
        //checkBoxYes.setStyle("-fx-text-fill: red; -fx-font-weight: bold; " +
        //        "-fx-border-color: inherit;");

        // inline style css
        //checkBoxYes.setStyle("-fx-text-fill: blue; -fx-font-weight: bold; -fx-font-size: 50;");

        // using JavaFX API
        //checkBoxYes.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    }
    /*
    public void cancelClick(ActionEvent actionEvent) {
        System.exit(0);
    }
    */

    public void menuItemAboutOnClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("About Maintainable GUI");
        alert.setContentText("This is to demonstrate the menu GUI");
        alert.show();
    }

    @FXML private ProgressBar progressBar;
    private ProgressWorker progressWorker = new ProgressWorker();

    public void doWorkOnClicked(ActionEvent actionEvent) {
        new Thread(progressWorker).start();
        progressBar.progressProperty().bind(progressWorker.progressProperty());
    }

    public void cancelOnClicked(ActionEvent actionEvent) {
        progressWorker.cancel();
    }
}

