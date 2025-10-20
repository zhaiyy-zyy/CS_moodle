module com.example.samplemvc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.samplemvc to javafx.fxml;
    exports com.example.samplemvc;
}