module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    opens stylethread to javafx.fxml;
    opens multiplewindows to javafx.fxml;
    opens animation to javafx.fxml;
    opens misc to javafx.fxml;
    exports stylethread;
    exports multiplewindows;
    exports animation;
    exports misc.animate3d;
    exports misc.animatedchart;
    exports misc.piechart;
    exports misc.playingvideo;
    exports misc.trianglemeshes;
    exports misc.webview;
}