package com.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;


    public void loginButton(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        System.out.println(username);
        System.out.println(password);
    }
}
