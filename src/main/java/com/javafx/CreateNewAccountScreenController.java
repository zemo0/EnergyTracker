package com.javafx;

import com.models.Administrator;
import com.models.Korisnik;
import com.models.Racun;
import com.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        Set<Racun> racuni = FileUtils.dohvatPodatakaORacunima();
        boolean areTextFieldsEqual = false;
        for(Racun racun : racuni){
            if (racun.getUsername().equals(username) && racun.getPassword().equals(password)) {
                areTextFieldsEqual = true;
                break;
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
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("USPJEŠAN UNOS");
            alert.setHeaderText("Uspješno uneseni podatci");
            alert.setContentText("Novi račun je kreiran");
            alert.showAndWait();
            if(role.equals("Korisnik")){
                Korisnik korisnik = new Korisnik.KorisnikBuilder().setUsername(username).setPassword(password).build();
                racuni.add(korisnik);
            } else {
                Administrator administrator = new Administrator.AdministratorBuilder().setUsername(username)
                        .setPassword(password).build();
                racuni.add(administrator);
            }

            //spremanje novog podatka kao korisnik
            String filePath = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\loginInfo.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
                for(Racun racun : racuni) {
                    writer.write(racun.getRole());
                    writer.newLine();
                    writer.write(racun.getUsername());
                    writer.newLine();
                    writer.write(FileUtils.hashPassword(racun.getPassword()));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
