module com.energytracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.slf4j;
    requires java.sql;


    opens com.javafxFiles to javafx.fxml;
    exports com.javafxFiles;
}