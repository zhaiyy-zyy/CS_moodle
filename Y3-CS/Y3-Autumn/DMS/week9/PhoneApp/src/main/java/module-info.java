module com.example.phoneapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.phoneapp to javafx.fxml;
    exports com.example.phoneapp;
}