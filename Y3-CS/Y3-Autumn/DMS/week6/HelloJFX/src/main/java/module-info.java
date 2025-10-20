module com.example.hellojfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hellojfx to javafx.fxml;
    exports com.example.hellojfx;
}