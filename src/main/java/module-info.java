module com.energytracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.javafx to javafx.fxml;
    exports com.javafx;
}