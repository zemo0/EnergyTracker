package com.javafxFiles;

import com.models.Category;
import com.utils.DatabaseUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.util.List;

import static com.mainPackage.Main.logger;

public class EnterNewCategoryController {
    @FXML
    private TextField categoryNameTextField;
    @FXML
    private TextField categoryDescriptionTextField;
    @FXML
    private TextField searchCategoryTextField;
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableColumn<Category, String> categoryNameTableColumn;
    @FXML
    private TableColumn<Category, String> categoryDescriptionTableColumn;
    public void initialize(){
        categoryNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Category, String> categoryStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(categoryStringCellDataFeatures.getValue().getName());
            }
        });
        categoryDescriptionTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Category, String> categoryStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(categoryStringCellDataFeatures.getValue().getDescription());
            }
        });
        List<Category> categories = DatabaseUtils.getAllCategories();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        categoryTableView.setItems(observableCategories);
    }

    public void addCategory(){
        String categoryName = categoryNameTextField.getText();
        String categoryDescription = categoryDescriptionTextField.getText();
        Category category = new Category.CategoryBuilder().name(categoryName).description(categoryDescription).build();
        DatabaseUtils.insertNewCategory(category);//unos nove kategorije
        List<Category> categories = DatabaseUtils.getAllCategories();//prikaz uz dodanu kategoriju
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        categoryTableView.setItems(observableCategories);
        clearFields();
    }
    public void searchCategory(){
        String searchCategory = searchCategoryTextField.getText();
        List<Category> categories = DatabaseUtils.getAllCategories();
        List<Category> sortedCategories = categories.stream()
                .filter(category -> category.getName().contains(searchCategory)).toList();
        if(sortedCategories.isEmpty()){
            sortedCategories = categories.stream()
                    .filter(category -> category.getDescription().contains(searchCategory)).toList();
        }
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(sortedCategories);
        categoryTableView.setItems(observableCategories);
    }
    public void changeCategory(){
        Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();//treba implementirat update tablice nakon izmjene jedne kategorije
        if(selectedCategory != null && !categoryNameTextField.getText().isEmpty() && !categoryDescriptionTextField.getText().isEmpty()){
            String categoryName = categoryNameTextField.getText();
            String categoryDescription = categoryDescriptionTextField.getText();
            Category category = new Category.CategoryBuilder().name(categoryName).description(categoryDescription).build();
            DatabaseUtils.updateCategory(category, selectedCategory.getId());
        } else if(categoryNameTextField.getText().isEmpty() || categoryDescriptionTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste unijeli podatke");
            alert.setContentText("Molimo vas da unesete kako biste htjeli da ti podatci izgledaju");
            alert.showAndWait();
            logger.info("Nisu uneseni podatci za promjenu kategorije");
        } else if(!categoryNameTextField.getText().isEmpty() && !categoryDescriptionTextField.getText().isEmpty() && selectedCategory == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste odabrali kategoriju");
            alert.setContentText("Molimo vas da odaberete kategoriju koju želite promijeniti");
            alert.showAndWait();
            logger.info("Kategorija za promjenu nije izabrana");
        }
        clearFields();
    }

    public void deleteCategory(){
        Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();//treba implementirat update tablice nakon izmjene jedne kategorije
        DatabaseUtils.deleteCategory(selectedCategory);
        clearFields();
    }
    public void clearFields(){
        categoryNameTextField.clear();
        categoryDescriptionTextField.clear();
    }
}
