package com.javafxFiles;

import com.FileUtilsThreads.SerializeAppliancesThread;
import com.Serialization.ApplianceSerialization;
import com.DatabaseThreads.GetAllCategoriesThread;
import com.mainPackage.Main;
import com.models.ElectricityCost;
import com.models.Months;
import com.utils.DatabaseUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.models.Category;
import com.models.Appliance;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.List;

import static com.javafxFiles.LoginController.currentUser;
import static com.mainPackage.Main.logger;

public final class ApplianceController implements CRUD_Methods{
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
    private ApplianceSerialization applianceSerialization = new ApplianceSerialization();
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
        GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
        List<Category> categories = getAllCategoriesThread.getAllCategories();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        categoryComboBox.setItems(observableCategories);
        ObservableList<String> observableTariffs = FXCollections.observableArrayList("Dnevna", "Noćna");
        tariffComboBox.setItems(observableTariffs);
        monthsComboBox.setItems(FXCollections.observableArrayList(Months.values()));//svi comboboxevi postavljeni
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        ObservableList<Appliance> observableAppliances = FXCollections.observableArrayList(appliances);
        applianceTableView.setItems(observableAppliances);
    }
    @Override
    public void add(){
        Category category = categoryComboBox.getValue();
        Months month = monthsComboBox.getValue();
        double appliancePowerUse = Double.parseDouble(appliancePowerUseTextField.getText());
        double dailyUseTime = Double.parseDouble(dailyUseTimeTextField.getText());
        Double dailyConsumption = (appliancePowerUse/1000) * dailyUseTime;
        String tariff = tariffComboBox.getValue();
        Double totalCostOfAppliance = tariff.equals("Dnevna") ? dailyConsumption * ElectricityCost.DAILY_RATE : dailyConsumption * ElectricityCost.NIGHT_RATE;
        Appliance appliance = new Appliance.ApplianceBuilder().category(category).username(currentUser.getUsername())
                .month(month).appliancePowerUse(appliancePowerUse).dailyUseTime(dailyUseTime).tariff("Dnevna".equals(tariff))
                .dailyConsumption(dailyConsumption).totalCostOfAppliance(totalCostOfAppliance)
                .build();
        applianceSerialization.addChangeInAppliances("Unos novog trošila");
        applianceSerialization.addApplianceBeforeChange(null);
        applianceSerialization.addApplianceAfterChange(appliance);
        applianceSerialization.addTimeOfChange(LocalDateTime.now());
        SerializeAppliancesThread serializeAppliancesThread = new SerializeAppliancesThread(applianceSerialization);
        serializeAppliancesThread.serializeAppliances(applianceSerialization);
        if(Main.checkForDuplicateAppliances(appliance)) { //ako je uneseno trošilo duplikat
            Main.addDuplicateAppliances(appliance);
            clearFields();
        } else {
            DatabaseUtils.insertNewAppliance(appliance);
            clearFields();
        }
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        ObservableList<Appliance> observableAppliances = FXCollections.observableArrayList(appliances);
        applianceTableView.setItems(observableAppliances);
    }
    @Override
    public void search(){
        String searchApplianceText = searchApplianceTextField.getText();
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        List<Appliance> sortedAppliances = appliances.stream()
                .filter(appliance -> appliance.getApplianceCategory().getName().contains(searchApplianceText)).toList();
        ObservableList<Appliance> observableAppliances = FXCollections.observableArrayList(sortedAppliances);
        applianceTableView.setItems(observableAppliances);
    }
    @Override
    public void change(){
        Appliance selectedAppliance = applianceTableView.getSelectionModel().getSelectedItem();
        if(selectedAppliance != null && categoryComboBox.getValue() != null && monthsComboBox.getValue() != null
                && !appliancePowerUseTextField.getText().isEmpty() && !dailyUseTimeTextField.getText().isEmpty()
                && tariffComboBox.getValue() != null){
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);//samo potvrda promjene podataka
            confirmationDialog.setTitle("Potvrda");
            confirmationDialog.setHeaderText("Jeste li sigurni da želite promjeniti podatke izabrane kategorije?");
            confirmationDialog.setContentText("Stisnite OK za potvrdu");
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Category category = categoryComboBox.getValue();
                    Months month = monthsComboBox.getValue();
                    double appliancePowerUse = Double.parseDouble(appliancePowerUseTextField.getText());
                    double dailyUseTime = Double.parseDouble(dailyUseTimeTextField.getText());
                    double dailyConsumption = (appliancePowerUse/1000) * dailyUseTime;
                    String tariff = tariffComboBox.getValue();
                    double totalCostOfAppliance = tariff.equals("Dnevna") ? dailyConsumption * ElectricityCost.DAILY_RATE : dailyConsumption * ElectricityCost.NIGHT_RATE;
                    Appliance appliance = new Appliance.ApplianceBuilder().category(category).username(currentUser.getUsername())
                            .month(month).appliancePowerUse(appliancePowerUse).dailyUseTime(dailyUseTime)
                            .tariff("Dnevna".equals(tariff)).dailyConsumption(dailyConsumption)
                            .totalCostOfAppliance(totalCostOfAppliance)
                            .build();
                    applianceSerialization.addChangeInAppliances("Promjena trošila");
                    applianceSerialization.addApplianceBeforeChange(selectedAppliance);
                    applianceSerialization.addApplianceAfterChange(appliance);
                    applianceSerialization.addTimeOfChange(LocalDateTime.now());
                    SerializeAppliancesThread serializeAppliancesThread = new SerializeAppliancesThread(applianceSerialization);
                    serializeAppliancesThread.serializeAppliances(applianceSerialization);
                    DatabaseUtils.updateAppliance(appliance, selectedAppliance.getId());
                    List<Appliance> appliances = DatabaseUtils.getAllAppliances();
                    ObservableList<Appliance> observableAppliances = FXCollections.observableArrayList(appliances);
                    applianceTableView.setItems(observableAppliances);
                    clearFields();
                } else {
                    logger.info("Promjena podataka trošila nije potvrđena");
                }
            });
        } else if(selectedAppliance == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste odabrali trošilo");
            alert.setContentText("Molimo vas da odaberete trošilo koje želite promijeniti");
            alert.showAndWait();
            logger.info("Appliance za promjenu nije izabrana");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste unijeli podatke");
            alert.setContentText("Molimo vas da unesete kako biste htjeli da ti podatci izgledaju");
            alert.showAndWait();
            logger.info("Nisu uneseni podatci za promjenu trošila");
        }
    }
    @Override
    public void delete(){
        Appliance selectedAppliance = applianceTableView.getSelectionModel().getSelectedItem();
        if(selectedAppliance != null){
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);//samo potvrda promjene podataka
            confirmationDialog.setTitle("Potvrda");
            confirmationDialog.setHeaderText("Jeste li sigurni da želite promjeniti podatke izabrane kategorije?");
            confirmationDialog.setContentText("Stisnite OK za potvrdu");
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    applianceSerialization.addChangeInAppliances("Brisanje trošila");
                    applianceSerialization.addApplianceBeforeChange(selectedAppliance);
                    applianceSerialization.addApplianceAfterChange(null);
                    applianceSerialization.addTimeOfChange(LocalDateTime.now());
                    SerializeAppliancesThread serializeAppliancesThread = new SerializeAppliancesThread(applianceSerialization);
                    serializeAppliancesThread.serializeAppliances(applianceSerialization);
                    DatabaseUtils.deleteAppliance(selectedAppliance);
                    List<Appliance> appliances = DatabaseUtils.getAllAppliances();
                    ObservableList<Appliance> observableAppliances = FXCollections.observableArrayList(appliances);
                    applianceTableView.setItems(observableAppliances);
                    clearFields();
                } else {
                    logger.info("Promjena podataka trošila nije potvrđena");
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste odabrali trošilo");
            alert.setContentText("Molimo vas da odaberete trošilo koje želite obrisati");
            alert.showAndWait();
            logger.info("Appliance za brisanje nije izabrana");
        }
    }
    public void clearFields(){
        categoryComboBox.getSelectionModel().clearSelection();
        appliancePowerUseTextField.clear();
        dailyUseTimeTextField.clear();
        tariffComboBox.getSelectionModel().clearSelection();
        monthsComboBox.getSelectionModel().clearSelection();
    }
}
