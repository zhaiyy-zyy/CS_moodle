module javafxapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens javafxapp to javafx.fxml;
    opens javafxbindingproperty to javafx.fxml;
    opens javafxfxml to javafx.fxml;
    opens javafxlayout to javafx.fxml;
    opens javafxpropertyfxml to javafx.fxml;
    opens javafxpropertynofxml to javafx.fxml;
    opens sample to javafx.fxml;
    exports javafxapp;
    exports javafxbindingproperty;
    exports javafxfxml;
    exports javafxlayout;
    exports javafxpropertyfxml;
    exports javafxpropertynofxml;
    exports sample;
}