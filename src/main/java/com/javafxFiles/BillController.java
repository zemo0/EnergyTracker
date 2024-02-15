package com.javafxFiles;

import com.models.Appliance;
import com.models.Category;
import com.models.Months;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import com.utils.DatabaseUtils;
import java.util.List;

public class BillController {
    @FXML
    private ComboBox<Months> monthComboBox;
    @FXML
    private TableView<Appliance> appliancesTableView;
    @FXML
    private TableColumn<Appliance, String> categoryNameTableColumn;
    @FXML
    private TableColumn<Appliance, Double> dailyConsumptionTableColumn;
    @FXML
    private TableColumn<Appliance, String> tariffTableColumn;
    @FXML
    private TableColumn<Appliance, Double> dailyCostTableColumn;
    @FXML
    private Label outputLabel;

    public void initialize(){
        categoryNameTableColumn.setCellValueFactory(categoryStringCellDataFeatures -> new ReadOnlyStringWrapper(categoryStringCellDataFeatures.getValue().getApplianceCategory().getName()));
        dailyConsumptionTableColumn.setCellValueFactory(applianceDoubleCellDataFeatures -> new ReadOnlyObjectWrapper<>(applianceDoubleCellDataFeatures.getValue().getDailyConsumption()));
        tariffTableColumn.setCellValueFactory(applianceStringCellDataFeatures -> new ReadOnlyStringWrapper(applianceStringCellDataFeatures.getValue().getTariff() ? "Dnevna" : "Noćna"));
        dailyCostTableColumn.setCellValueFactory(applianceDoubleCellDataFeatures -> new ReadOnlyObjectWrapper<>(applianceDoubleCellDataFeatures.getValue().getTotalCostOfAppliance()));
        monthComboBox.getItems().setAll(Months.values());
    }
    public void showByMonth(){
        List<Appliance> appliances = DatabaseUtils.getAppliancesByMonth(String.valueOf(monthComboBox.getValue()));
        ObservableList<Appliance> observableApplianceList = FXCollections.observableArrayList(appliances);
        appliancesTableView.setItems(observableApplianceList);
        Double totalCost = appliances.stream().mapToDouble(Appliance::getTotalCostOfAppliance).sum();
        String formattedTotalCost = String.format("%.2f", totalCost);
        String outputText = "Ukupna potrošnja za " + monthComboBox.getValue() + " je " + formattedTotalCost + " eura";
        outputLabel.setText(outputText);
    }

}
