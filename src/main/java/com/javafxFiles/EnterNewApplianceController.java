package com.javafxFiles;

import com.models.Months;
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
    private ComboBox<Months> monthsComboBox;
    @FXML
    private TextField appliancePowerUseTextField;
    @FXML
    private TextField dailyUseTimeTextField;
    @FXML
    private ComboBox<String> tariffComboBox;
    @FXML
    private TextField searchApplianceTextField;
    @FXML
    private TableView<Appliance> applianceTableView;
    @FXML
    private TableColumn<Appliance, String> applianceCategoryTableColumn;
    @FXML
    private TableColumn<Appliance, String> applianceMonthTableColumn;
    @FXML
    private TableColumn<Appliance, Double> appliancePowerUseTableColumn;
    @FXML
    private TableColumn<Appliance, Double> appliancedDailyUseTimeTableColumn;
    @FXML
    private TableColumn<Appliance, String> applianceTariffTableColumn;
    @FXML
    private TableColumn<Appliance, Double> applianceDailyConsumptionTableColumn;

    public void initialize(){
        applianceCategoryTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appliance, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appliance, String> applianceStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(applianceStringCellDataFeatures.getValue().getApplianceCategory().getName());
            }
        });
        applianceMonthTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appliance, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appliance, String> applianceStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(applianceStringCellDataFeatures.getValue().getMonth());
            }
        });
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
        applianceTariffTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appliance, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appliance, String> applianceStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(applianceStringCellDataFeatures.getValue().getTariff() ? "Dnevna" : "Noćna");
            }
        });
        applianceDailyConsumptionTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appliance, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Appliance, Double> applianceDoubleCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(applianceDoubleCellDataFeatures.getValue().getDailyConsumption());
            }
        });
        List<Category> categories = DatabaseUtils.getAllCategories();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        categoryComboBox.setItems(observableCategories);
        ObservableList<String> observableTariffs = FXCollections.observableArrayList("Dnevna", "Noćna");
        tariffComboBox.setItems(observableTariffs);
        monthsComboBox.setItems(FXCollections.observableArrayList(Months.values()));//svi comboboxevi postavljeni
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        ObservableList<Appliance> observableAppliances = FXCollections.observableArrayList(appliances);
        applianceTableView.setItems(observableAppliances);
    }
    public void addAppliance(){
        Category category = categoryComboBox.getValue();
        Months month = monthsComboBox.getValue();
        Double appliancePowerUse = Double.parseDouble(appliancePowerUseTextField.getText());
        Double dailyUseTime = Double.parseDouble(dailyUseTimeTextField.getText());
        String tariff = tariffComboBox.getValue();
        Double dailyConsumption = (appliancePowerUse/1000) * dailyUseTime;
        Appliance appliance = new Appliance.ApplianceBuilder().category(category).month(month)
                .appliancePowerUse(appliancePowerUse).dailyUseTime(dailyUseTime).tariff("Dnevna".equals(tariff))
                .dailyConsumption(dailyConsumption).build();
        DatabaseUtils.insertNewAppliance(appliance);
    }
    public void searchAppliance(){
        String searchApplianceText = searchApplianceTextField.getText();
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        List<Appliance> sortedAppliances = appliances.stream()
                .filter(appliance -> appliance.getApplianceCategory().getName().contains(searchApplianceText)).toList();
        ObservableList<Appliance> observableAppliances = FXCollections.observableArrayList(sortedAppliances);
        applianceTableView.setItems(observableAppliances);
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
