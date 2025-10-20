module com.example.fxmaptreasurehunt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.fxmaptreasurehunt to javafx.fxml;
    exports com.example.fxmaptreasurehunt;
    opens com.example.fxmaptreasurehunt.controller to javafx.fxml;
}