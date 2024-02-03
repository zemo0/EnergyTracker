package com.javafxFiles;

import com.utils.DatabaseUtils;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.models.Category;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.models.Appliance;
import javafx.util.Callback;

import java.util.List;

public class EnterNewApplianceController {
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private TextField appliancePowerUseTextField;
    @FXML
    private TextField dailyUseTimeTextField;
    @FXML
    private ComboBox<String> tariffComboBox;
    @FXML
    private TableView<Appliance> applianceTableView;
    @FXML
    private TableColumn<Appliance, String> applianceCategoryTableColumn;
    @FXML
    private TableColumn<Appliance, Double> appliancePowerUseTableColumn;
    @FXML
    private TableColumn<Appliance, Double> appliancedDailyUseTimeTableColumn;
    @FXML
    private TableColumn<Appliance, String> applianceTariffTableColumn;
    @FXML
    private TableColumn<Appliance, Double> applianceDailyConsumptionTableColumn;

    public void initialize(){
        appliancePowerUseTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appliance, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Appliance, Double> applianceDoubleCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(applianceDoubleCellDataFeatures.getValue().getAppliancePowerUse());
            }
        });
        appliancedDailyUseTimeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appliance, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Appliance, Double> applianceDoubleCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(applianceDoubleCellDataFeatures.getValue().getDailyUseTime());
            }
        });
        List<Category> categories = DatabaseUtils.getAllCategories();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        categoryComboBox.setItems(observableCategories);
        ObservableList<String> observableTariffs = FXCollections.observableArrayList("Dnevna", "Noćna");
        tariffComboBox.setItems(observableTariffs);
    }
    public void addAppliance(){
        Category category = categoryComboBox.getValue();
        Double appliancePowerUse = Double.parseDouble(appliancePowerUseTextField.getText());
        Double dailyUseTime = Double.parseDouble(dailyUseTimeTextField.getText());
        String tariff = tariffComboBox.getValue();
        Appliance appliance = new Appliance.ApplianceBuilder().category(category)
                .appliancePowerUse(appliancePowerUse).dailyUseTime(dailyUseTime).tariff("Dnevna".equals(tariff)).build();
        DatabaseUtils.insertNewAppliance(appliance);
    }
    public void searchAppliance(){

    }
    public void changeAppliance(){

    }
    public void deleteAppliance(){

    }
    public void clearFields(){
        categoryComboBox.getSelectionModel().clearSelection();
        appliancePowerUseTextField.clear();
        dailyUseTimeTextField.clear();
        tariffComboBox.getSelectionModel().clearSelection();
    }
}
