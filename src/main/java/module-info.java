module com.energytracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.energytracker to javafx.fxml;
    exports com.energytracker;
}