package com.javafxFiles;

import com.models.Appliance;
import com.models.Category;
import com.models.Months;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import com.utils.DatabaseUtils;
import java.util.List;

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
        categoryNameTableColumn.setCellValueFactory(categoryStringCellDataFeatures -> new ReadOnlyStringWrapper(categoryStringCellDataFeatures.getValue().getName()));
        dailyConsumptionTableColumn.setCellValueFactory(applianceDoubleCellDataFeatures -> new ReadOnlyObjectWrapper<>(applianceDoubleCellDataFeatures.getValue().getDailyConsumption()));
        tariffTableColumn.setCellValueFactory(applianceStringCellDataFeatures -> new ReadOnlyStringWrapper(applianceStringCellDataFeatures.getValue().getTariff() ? "Dnevna" : "Noćna"));
        dailyCostTableColumn.setCellValueFactory(applianceDoubleCellDataFeatures -> new ReadOnlyObjectWrapper<>(applianceDoubleCellDataFeatures.getValue().getDailyConsumption()));
        monthComboBox.getItems().setAll(Months.values());
    }
    public void showCostsByMonth(){
        String month = String.valueOf(monthComboBox.getSelectionModel().getSelectedItem());
        List<Appliance> appliances = DatabaseUtils.getAppliancesByMonth(month);
        for(Appliance appliance : appliances){
            System.out.println(appliance.getApplianceCategory().getName() + " " + appliance.getDailyConsumption() + " " + appliance.getTariff() + " " + appliance.getDailyConsumption());
        }
    }

}
