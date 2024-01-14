package com.javafx;

import com.models.Korisnik;
import com.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreateNewAccountScreenController {
    @FXML
    private ComboBox<String> roleSelectorComboBox;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    public void initialize(){
        List<String> listOfRoles= new ArrayList<>();
        listOfRoles.add("Korisnik");
        listOfRoles.add("Administrator");
        ObservableList<String> observableListOfRoles = FXCollections.observableArrayList(listOfRoles);
        roleSelectorComboBox.setItems(observableListOfRoles);
    }

    public void onButtonClick(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String role = roleSelectorComboBox.getValue();
        Set<Korisnik> korisnici = FileUtils.dohvatPodatakaOKorisnicima();
        boolean areTextFieldsEqual = false;
        for(Korisnik korisnik : korisnici){
            if(korisnik.getUsername().equals(username) &&
                    korisnik.getPassword().equals(password)){
                areTextFieldsEqual = true;
            }
        }
        if(areTextFieldsEqual){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Dupli unos podataka");
            alert.setContentText("Već postoji korisnički račun s tim podatcima. Molimo probajte s novim podatcima");
            alert.showAndWait();
        } else if(username.isEmpty() || password.isEmpty() || role.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Nisu uneseni svi podatci");
            alert.setContentText("Jedno polje vam je ostalo prazno, molim vas popunite sva potrebna polja.");
            alert.showAndWait();
        }
    }
}
