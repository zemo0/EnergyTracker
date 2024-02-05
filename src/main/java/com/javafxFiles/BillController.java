package com.javafxFiles;

import com.models.Appliance;
import com.models.Category;
import com.models.Months;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;

public class BillController {
    @FXML
    private ComboBox<Months> monthComboBox;
    @FXML
    private TableColumn<Category, String> categoryNameTableColumn;
    @FXML
    private TableColumn<Appliance, Double> dailyConsumptionTableColumn;
    @FXML
    private TableColumn<Appliance, String> tariffTableColumn;
    @FXML
    private TableColumn<Appliance, Double> dailyCostTableColumn;

    public void initialize(){
        monthComboBox.getItems().setAll(Months.values());
    }

}
