module com.example.advphoneapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.advphoneapp to javafx.fxml;
    exports com.example.advphoneapp;
}