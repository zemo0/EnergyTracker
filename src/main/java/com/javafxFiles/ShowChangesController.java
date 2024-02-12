package com.javafxFiles;

import com.Serialization.ApplianceSerialization;
import com.Serialization.CategorySerialization;
import com.models.Role;
import com.utils.DatabaseUtils;
import com.utils.FileUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class ShowChangesController {
    @FXML
    private TextArea changesTextArea;
    private String dateTimeFormatter = "dd.MM.yyyy HH:mm:ss";

    public void initialize(){
        changesTextArea.setText("Stvoreni korisnici: \n");
        for(Role r : FileUtils.deserializeRacune()) {
            changesTextArea.appendText("Rola: " + r.getRole() + ", korisničko ime: " + r.getUsername() + "\n");
        }
        changesTextArea.appendText("-----------------------------\n");
        changesTextArea.appendText("\n");
        changesTextArea.appendText("Promjene u kategorijama: \n");
        CategorySerialization categorySerialization = FileUtils.deserializeCategories();
        for(int i = 0; i < categorySerialization.getChangeInCategories().size(); i++){
            changesTextArea.appendText("Promjena: " + categorySerialization.getChangeInCategories().get(i) + "\n");
            changesTextArea.appendText("Prije promjene: " + categorySerialization.getCategoriesBeforeChange().get(i) + "\n");
            changesTextArea.appendText("Nakon promjene: " + categorySerialization.getCategoriesAfterChange().get(i) + "\n");
            changesTextArea.appendText("Vrijeme promjene: " + categorySerialization.getTimeOfChange().get(i).format(DateTimeFormatter.ofPattern(dateTimeFormatter)) + "\n");
            changesTextArea.appendText("\n");
        }
        changesTextArea.appendText("-----------------------------\n");
        changesTextArea.appendText("Promjene u uređajima: \n");
        ApplianceSerialization applianceSerialization = FileUtils.deserializeAppliances();
        for(int i = 0; i < applianceSerialization.getChangeInAppliances().size(); i++){
            changesTextArea.appendText("Promjena: " + applianceSerialization.getChangeInAppliances().get(i) + "\n");
            changesTextArea.appendText("Prije promjene: " + applianceSerialization.getAppliancesBeforeChange().get(i) + "\n");
            changesTextArea.appendText("Nakon promjene: " + applianceSerialization.getAppliancesAfterChange().get(i) + "\n");
            changesTextArea.appendText("Vrijeme promjene: " + applianceSerialization.getTimeOfChange().get(i).format(DateTimeFormatter.ofPattern(dateTimeFormatter)) + "\n");
            changesTextArea.appendText("\n");
        }
        changesTextArea.appendText("Promjene napravio: " + LoginController.currentUser);
    }
}
