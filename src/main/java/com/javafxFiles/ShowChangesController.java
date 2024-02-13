package com.javafxFiles;

import com.FileUtilsThreads.DeserializeAppliancesThread;
import com.FileUtilsThreads.DeserializeCategoriesThread;
import com.FileUtilsThreads.DeserializeRacuneThread;
import com.FileUtilsThreads.FileUtilsThread;
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

import static com.javafxFiles.LoginController.currentUser;

public class ShowChangesController {
    @FXML
    private TextArea changesTextArea;
    private String dateTimeFormatter = "dd.MM.yyyy HH:mm:ss";

    public void initialize(){
        changesTextArea.setText("Stvoreni korisnici: \n");
        DeserializeRacuneThread deserializeRacuneThread = new DeserializeRacuneThread();
        for(Role r : deserializeRacuneThread.deserializeRacune()) {
            changesTextArea.appendText("Rola: " + r.getRole() + ", korisničko ime: " + r.getUsername() + "\n");
        }
        changesTextArea.appendText("-----------------------------\n");
        changesTextArea.appendText("\n");
        changesTextArea.appendText("Promjene u kategorijama: \n");
        DeserializeCategoriesThread deserializeCategoriesThread = new DeserializeCategoriesThread();
        CategorySerialization categorySerialization = deserializeCategoriesThread.deserializeCategories();
        for(int i = 0; i < categorySerialization.getChangeInCategories().size(); i++){
            changesTextArea.appendText("Promjena: " + categorySerialization.getChangeInCategories().get(i) + "\n");
            changesTextArea.appendText("Prije promjene: " + categorySerialization.getCategoriesBeforeChange().get(i) + "\n");
            changesTextArea.appendText("Nakon promjene: " + categorySerialization.getCategoriesAfterChange().get(i) + "\n");
            changesTextArea.appendText("Vrijeme promjene: " + categorySerialization.getTimeOfChange().get(i).format(DateTimeFormatter.ofPattern(dateTimeFormatter)) + "\n");
            changesTextArea.appendText("\n");
        }
        changesTextArea.appendText("-----------------------------\n");
        changesTextArea.appendText("Promjene u uređajima: \n");
        DeserializeAppliancesThread deserializeAppliancesThread = new DeserializeAppliancesThread();
        ApplianceSerialization applianceSerialization = deserializeAppliancesThread.deserializeAppliances();
        for(int i = 0; i < applianceSerialization.getChangeInAppliances().size(); i++){
            changesTextArea.appendText("Promjena: " + applianceSerialization.getChangeInAppliances().get(i) + "\n");
            changesTextArea.appendText("Prije promjene: " + applianceSerialization.getAppliancesBeforeChange().get(i) + "\n");
            changesTextArea.appendText("Nakon promjene: " + applianceSerialization.getAppliancesAfterChange().get(i) + "\n");
            changesTextArea.appendText("Vrijeme promjene: " + applianceSerialization.getTimeOfChange().get(i).format(DateTimeFormatter.ofPattern(dateTimeFormatter)) + "\n");
            changesTextArea.appendText("Promjenio korisnik: " + currentUser.getUsername() + "\n");
            changesTextArea.appendText("\n");
        }
    }
}
