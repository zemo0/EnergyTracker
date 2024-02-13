package com.javafxFiles;

import com.Exceptions.DuplicateUserException;
import com.Exceptions.FileNotCorrectException;
import com.FileUtilsThreads.DohvatRacunaThread;
import com.FileUtilsThreads.SerializeRacuneThread;
import com.models.Admin;
import com.models.User;
import com.models.Role;
import com.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mainPackage.Main.logger;

public class CreateNewAccountController {
    public static Integer changeCounter = 0;
    private Set<Role> changesInRoles = new HashSet<>();
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
        Set<Role> racuni = new HashSet<>();
        try {
            DohvatRacunaThread dohvatRacunaThread = new DohvatRacunaThread();
            racuni = dohvatRacunaThread.dohvatPodatakaORacunima();
        } catch (FileNotCorrectException e) {
            e.printStackTrace();
            logger.info("Datoteka s korisnicima nije ispravna, nedostaje neki podatak");
        }
        boolean areTextFieldsEqual = false;
        try {
            areTextFieldsEqual = checkIfUserExists(username, password, racuni);
        } catch (DuplicateUserException e) {
            areTextFieldsEqual = true;
            e.printStackTrace();
            logger.info("Korisnik kojeg pokušavate kreirati već postoji u sustavu");
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
            Role bill;
            if(role.equals("Korisnik")){
                bill = new User.KorisnikBuilder().setUsername(username).setPassword(password).build();
            } else {
                bill = new Admin.AdministratorBuilder().setUsername(username)
                        .setPassword(password).build();
            }
            racuni.add(bill);
            changesInRoles.add(bill);
            //spremanje novog podatka kao korisnik
            String filePath = "C:\\Users\\Zemo\\IdeaProjects\\EnergyTracker\\files\\loginInfo.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
                for(Role out : racuni) {
                    writer.write(out.getRole());
                    writer.newLine();
                    writer.write(out.getUsername());
                    writer.newLine();
                    writer.write(FileUtils.hashPassword(out.getPassword()));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("Došlo je do greške pri zapisu novih korisnika u file");
            }
        }
    }
    public boolean checkIfUserExists(String username, String password, Set<Role> racuni) throws DuplicateUserException{
        boolean areTextFieldsEqual = false;
        for(Role bill : racuni){
            if (bill.getUsername().equals(username) && bill.getPassword().equals(password)) {
                areTextFieldsEqual = true;
                break;
            }
        }
        if(areTextFieldsEqual){
            throw new DuplicateUserException("Taj korisnik već postoji u sustavu.");
        }
        return areTextFieldsEqual;
    }

    public void showLoginScreen(){
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "loginScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 850, 600);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Došlo je do greške pri učitavanju nove scene");
        }

        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
        SerializeRacuneThread serializeRacuneThread = new SerializeRacuneThread(changesInRoles);
        serializeRacuneThread.serializeRacune(changesInRoles);
        changeCounter = changesInRoles.size();
    }
}
