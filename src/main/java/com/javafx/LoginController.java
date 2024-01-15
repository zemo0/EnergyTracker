package com.javafx;

import com.models.Racun;
import com.utils.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Set;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    public void loginButton(){
        Set<Racun> racuni = FileUtils.dohvatPodatakaORacunima();
        String usernameTextFieldText = usernameTextField.getText();
        String passwordPasswordFieldText = passwordTextField.getText();
        boolean areTextFieldsEqual = false;
        for(Racun racun : racuni){
            if (racun.getUsername().equals(usernameTextFieldText) &&
                    racun.getPassword().equals(passwordPasswordFieldText)) {
                areTextFieldsEqual = true;
                break;
            }
        }
        if(areTextFieldsEqual){
            showMainScreen();
        } else if(usernameTextFieldText.isEmpty() || passwordPasswordFieldText.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste unijeli podatke");
            alert.setContentText("Molimo Vas da unesete podatke za ulaz u sustav.");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Krivo uneseni podatci");
            alert.setContentText("Molimo Vas da unesete ispravne podatke za ulaz u sustav.");
            alert.showAndWait();
        }
    }
    public void showCreateNewAccountScreen(){
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "createNewAccountScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 850, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }
    public void showMainScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 850, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }

}
