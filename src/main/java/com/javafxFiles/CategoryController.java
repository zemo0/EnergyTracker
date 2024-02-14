package com.javafxFiles;

import com.FileUtilsThreads.SerializeCategoriesThread;
import com.Serialization.CategorySerialization;
import com.DatabaseThreads.GetAllCategoriesThread;
import com.mainPackage.Main;
import com.models.Category;
import com.utils.DatabaseUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.List;

import static com.javafxFiles.LoginController.currentUser;
import static com.mainPackage.Main.logger;

public final class CategoryController implements CRUD_Methods{
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
    @FXML
    private Button changeCategoryButton;
    private CategorySerialization categorySerialization = new CategorySerialization();
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
        GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
        List<Category> categories = getAllCategoriesThread.getAllCategories();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        categoryTableView.setItems(observableCategories);
    }
    @Override
    public void add(){
        String categoryName = categoryNameTextField.getText();
        String categoryDescription = categoryDescriptionTextField.getText();
        Category category = new Category.CategoryBuilder().name(categoryName).description(categoryDescription).build();
        DatabaseUtils.insertNewCategory(category);//unos nove kategorije
        GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
        List<Category> categories = getAllCategoriesThread.getAllCategories();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        categoryTableView.setItems(observableCategories);
        clearFields();
        categorySerialization.addCategoryBeforeChange(null);
        categorySerialization.addCategoryAfterChange(category);
        categorySerialization.addChangeInCategories("Unos nove kategorije");
        categorySerialization.addRoleThatChanged(currentUser.getUsername());
        categorySerialization.addTimeOfChange(java.time.LocalDateTime.now());
        SerializeCategoriesThread serializeCategoriesThread = new SerializeCategoriesThread(categorySerialization);
        serializeCategoriesThread.serializeCategories(categorySerialization);
    }
    @Override
    public void search(){
        String searchCategory = searchCategoryTextField.getText();
        GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
        List<Category> categories = getAllCategoriesThread.getAllCategories();
        List<Category> sortedCategories = categories.stream()
                .filter(category -> category.getName().contains(searchCategory)).toList();
        if(sortedCategories.isEmpty()){
            sortedCategories = categories.stream()
                    .filter(category -> category.getDescription().contains(searchCategory)).toList();
        }
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(sortedCategories);
        categoryTableView.setItems(observableCategories);
    }
    @Override
    public void change(){
        Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();//treba implementirat update tablice nakon izmjene jedne kategorije
        if(selectedCategory != null && !categoryNameTextField.getText().isEmpty() && !categoryDescriptionTextField.getText().isEmpty()){
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);//samo potvrda promjene podataka
            confirmationDialog.setTitle("Potvrda");
            confirmationDialog.setHeaderText("Jeste li sigurni da želite promjeniti podatke izabrane kategorije?");
            confirmationDialog.setContentText("Stisnite OK za potvrdu");
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String categoryName = categoryNameTextField.getText();
                    String categoryDescription = categoryDescriptionTextField.getText();
                    Category category = new Category.CategoryBuilder().name(categoryName).description(categoryDescription).build();
                    //serijalizacija
                    categorySerialization.addCategoryBeforeChange(selectedCategory);
                    categorySerialization.addCategoryAfterChange(category);
                    categorySerialization.addChangeInCategories("Izmjena kategorije");
                    categorySerialization.addRoleThatChanged(currentUser.getUsername());
                    categorySerialization.addTimeOfChange(java.time.LocalDateTime.now());
                    SerializeCategoriesThread serializeCategoriesThread = new SerializeCategoriesThread(categorySerialization);
                    serializeCategoriesThread.serializeCategories(categorySerialization);
                    DatabaseUtils.updateCategory(category, selectedCategory.getId()); // izmjena
                    GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
                    List<Category> categories = getAllCategoriesThread.getAllCategories();
                    ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
                    categoryTableView.setItems(observableCategories);
                } else {
                    logger.info("Promjena podataka kategorije nije potvrđena");
                }
            });
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
    @Override
    public void delete(){
        Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();//treba implementirat update tablice nakon izmjene jedne kategorije
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);//samo potvrda promjene podataka
        confirmationDialog.setTitle("Potvrda");
        confirmationDialog.setHeaderText("Jeste li sigurni da želite promjeniti podatke izabrane kategorije?");
        confirmationDialog.setContentText("Stisnite OK za potvrdu");
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if(Main.isCategoryUsedInAppliances(selectedCategory)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("GREŠKA KOD BRISANJA");
                    alert.setHeaderText("Kategorija se koristi u nekom od aparata");
                    alert.setContentText("Molimo vas da prvo promijenite kategoriju u svim aparatima");
                    alert.showAndWait();
                    logger.info("Kategorija se koristi u nekom od aparata");
                }else {
                    categorySerialization.addCategoryBeforeChange(selectedCategory);//serijalizacija
                    categorySerialization.addCategoryAfterChange(null);
                    categorySerialization.addChangeInCategories("Brisanje kategorije");
                    categorySerialization.addRoleThatChanged(currentUser.getUsername());
                    categorySerialization.addTimeOfChange(java.time.LocalDateTime.now());
                    SerializeCategoriesThread serializeCategoriesThread = new SerializeCategoriesThread(categorySerialization);
                    serializeCategoriesThread.serializeCategories(categorySerialization);
                    DatabaseUtils.deleteCategory(selectedCategory);
                    clearFields();
                }
            } else {
                logger.info("Promjena podataka kategorije nije potvrđena");
            }
        });
        GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
        List<Category> categories = getAllCategoriesThread.getAllCategories();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        categoryTableView.setItems(observableCategories);
    }
    public void clearFields(){
        categoryNameTextField.clear();
        categoryDescriptionTextField.clear();
    }
}
