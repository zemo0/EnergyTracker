module com.energytracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.fontawesomefx.fontawesome;


    opens com.javafx to javafx.fxml;
    exports com.javafx;
}