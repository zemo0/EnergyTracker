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
        System.out.println(korisnici);
    }

}
