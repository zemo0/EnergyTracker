package com.javafxFiles;

import com.utils.DatabaseUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EnterNewCategoryController {
    @FXML
    private TextField categoryNameTextField;
    @FXML
    private TextField categoryDescriptionTextField;

    public void dodajTrosilo(){
        String categoryName = categoryNameTextField.getText();
        String categoryDescription = categoryDescriptionTextField.getText();
        System.out.println(DatabaseUtils.getAllAppliances());
    }
}
