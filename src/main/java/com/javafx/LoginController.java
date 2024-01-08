package com.javafx;

import com.models.Korisnik;
import com.utils.FileUtils;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    public void loginButton(){
        List<Korisnik> korisnici = FileUtils.dohvatPodatakaOKorisnicima();
        String usernameTextFieldText = usernameTextField.getText();
        String passwordPasswordFieldText = passwordTextField.getText();
        boolean areTextFieldsEqual = false;
        for(Korisnik korisnik : korisnici){
            if(korisnik.getUsername().equals(usernameTextFieldText) &&
               korisnik.getPassword().equals(passwordPasswordFieldText)){
                areTextFieldsEqual = true;
            }
        }
        if(areTextFieldsEqual){
            //open new screen batonga
        }
    }

}
