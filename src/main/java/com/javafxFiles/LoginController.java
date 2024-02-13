package com.javafxFiles;

import com.Exceptions.FileNotCorrectException;
import com.Exceptions.InvalidCredentialsException;
import com.FileUtilsThreads.DohvatRacunaThread;
import com.models.Role;
import com.utils.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.mainPackage.Main.logger;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    public static Role currentUser;
    public void loginButton(){
        Set<Role> racuni = new HashSet<>();
        try {
            DohvatRacunaThread dohvatRacunaThread = new DohvatRacunaThread();
            racuni = dohvatRacunaThread.dohvatPodatakaORacunima();
        } catch (FileNotCorrectException e) {
            e.printStackTrace();
            logger.info("Datoteka s korisnicima nije ispravna, nedostaje neki podatak");
        }
        String usernameTextFieldText = usernameTextField.getText();
        String passwordPasswordFieldText = passwordTextField.getText();
        boolean areTextFieldsEqual = false;
        try {
            areTextFieldsEqual = checkIfUserExists(usernameTextFieldText, passwordPasswordFieldText, racuni);
        } catch (InvalidCredentialsException e) {
            e.printStackTrace();
            logger.info("Krivo uneseni podatci za prijavu u sustav.");
        }
        if(areTextFieldsEqual){
            currentUser = racuni.stream().filter(role -> role.getUsername().equals(usernameTextFieldText)).findFirst().get();
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
    public boolean checkIfUserExists(String usernameTextFieldText, String passwordTextFieldText, Set<Role> racuni) throws InvalidCredentialsException {
        boolean areTextFieldsEqual = false;
        for(Role role : racuni){
            if (role.getUsername().equals(usernameTextFieldText) &&
                    role.getPassword().equals(passwordTextFieldText)) {
                areTextFieldsEqual = true;
                break;
            }
        }
        if(!areTextFieldsEqual){
            throw new InvalidCredentialsException("Krivo uneseni podatci za prijavu u sustav.");
        }
        return areTextFieldsEqual;
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
                        "homeScreen.fxml"));
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
