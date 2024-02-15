package com.javafxFiles;

import com.FileUtilsThreads.SerializeRacuneThread;
import com.models.Role;
import com.utils.FileUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import com.models.Admin;
import com.models.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.javafxFiles.CreateNewAccountController.changesInRoles;
import static com.javafxFiles.LoginController.currentUser;
import static com.mainPackage.Main.logger;
import static com.javafxFiles.CreateNewAccountController.changeCounter;

public class UsersController {
    @FXML
    private ComboBox<String> rolesComboBox;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField searchBarTextField;
    @FXML
    private TableColumn<Role, String> roleTableColumn;
    @FXML
    private TableColumn<Role, String> usernameTableColumn;
    @FXML
    private TableColumn<Role, String> passwordTableColumn;
    @FXML
    private TableView<Role> usersTableView;
    public void initialize(){
        rolesComboBox.getItems().addAll("Administrator", "Korisnik");
        roleTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Role, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Role, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getRole());
            }
        });
        usernameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Role, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Role, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getUsername());
            }
        });
        passwordTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Role, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Role, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getPassword());
            }
        });
        if(currentUser.getRole().equals("Administrator")) {
            Set<Role> korisnici = FileUtils.dohvatPodatakaORacunima();
            usersTableView.getItems().addAll(korisnici);
        }
    }
    public void editRole(){
        Role selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null && rolesComboBox.getValue() != null && usernameTableColumn.getText()!=null && passwordTableColumn.getText()!=null){
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);//samo potvrda promjene podataka
            confirmationDialog.setTitle("Potvrda");
            confirmationDialog.setHeaderText("Jeste li sigurni da želite promjeniti podatke izabrane kategorije?");
            confirmationDialog.setContentText("Stisnite OK za potvrdu");
            confirmationDialog.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            String username = usernameTextField.getText();
                            String password = passwordTextField.getText();
                            String role = rolesComboBox.getValue();
                            if (role.equals("Administrator")) {
                                Admin admin = new Admin.AdministratorBuilder().setUsername(username).setPassword(password).build();
                                Set<Role> racuni = FileUtils.dohvatPodatakaORacunima();
                                racuni.remove(selectedUser);
                                racuni.add(admin);
                                changesInRoles.add(admin);
                                zapisRacuna(racuni);
                            } else {
                                User user = new User.KorisnikBuilder().setUsername(username).setPassword(password).build();
                                Set<Role> racuni = FileUtils.dohvatPodatakaORacunima();
                                racuni.remove(selectedUser);
                                racuni.add(user);
                                changesInRoles.add(user);
                                zapisRacuna(racuni);
                            }
                        }
                    });
        } else if(selectedUser == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste odabrali račun");
            alert.setContentText("Molimo vas da odaberete račun koji želite promijeniti");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste unijeli podatke");
            alert.setContentText("Molimo vas da unesete kako biste htjeli da ti podatci izgledaju");
            alert.showAndWait();
            logger.info("Nisu uneseni podatci za promjenu računa");
        }
        SerializeRacuneThread serializeRacuneThread = new SerializeRacuneThread(changesInRoles);
        serializeRacuneThread.serializeRacune(changesInRoles);
        if(currentUser.getRole().equals("Administrator")) {
            Set<Role> korisnici = FileUtils.dohvatPodatakaORacunima();
            usersTableView.getItems().clear();
            usersTableView.getItems().addAll(korisnici);
        }
    }

    public void deleteRole(){
        Role selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);//samo potvrda promjene podataka
            confirmationDialog.setTitle("Potvrda");
            confirmationDialog.setHeaderText("Jeste li sigurni da želite obrisati izabrani račun?");
            confirmationDialog.setContentText("Stisnite OK za potvrdu");
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Set<Role> racuni = FileUtils.dohvatPodatakaORacunima();
                    racuni.remove(selectedUser);
                    zapisRacuna(racuni);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("GREŠKA KOD UNOSA");
            alert.setHeaderText("Niste odabrali račun");
            alert.setContentText("Molimo vas da odaberete račun koji želite obrisati");
            alert.showAndWait();
        }
        SerializeRacuneThread serializeRacuneThread = new SerializeRacuneThread(changesInRoles);
        serializeRacuneThread.serializeRacune(changesInRoles);
        if(currentUser.getRole().equals("Administrator")) {
            Set<Role> korisnici = FileUtils.dohvatPodatakaORacunima();
            usersTableView.getItems().clear();
            usersTableView.getItems().addAll(korisnici);
        }
    }
    public void search(){
        String search = searchBarTextField.getText();
        Set<Role> racuni = FileUtils.dohvatPodatakaORacunima();
        Set<Role> filteredRacuni = new HashSet<>();
        for(Role r : racuni){
            if(r.getUsername().contains(search)){
                filteredRacuni.add(r);
            }
        }
        usersTableView.getItems().clear();
        usersTableView.getItems().addAll(filteredRacuni);
    }

    public void zapisRacuna(Set<Role> racuni){
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
        changeCounter++;
    }
}
