module com.example.platformer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.media;


    exports com.example.platformer.controller;
    exports com.example.platformer.Abstract;
    exports com.example.platformer.Factory;
    opens com.example.platformer.controller to javafx.fxml;
    exports com.example.platformer.model;
    exports com.example.platformer.view;
    exports com.example.platformer;
    exports com.example.platformer.Strategy;
    exports com.example.platformer.Decorator;
    opens com.example.platformer to javafx.fxml;
    exports com.example.platformer.State;
    opens com.example.platformer.State to javafx.fxml;
}
