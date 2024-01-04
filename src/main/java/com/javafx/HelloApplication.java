package com.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Ideje za app:
 * Grafički prikaz potrošnje po danima, tjednima, mjesecima?
 * Personalizirane poruke za uštedu energije
 * Integracija s pametnim uređajima
 * Obavjestiti o vrhuncu
 * Pokazati potrošnju npr u količini CO2 emisije
 * Na sve staviti lijepi CSS
 *
 * DASHBOARD:
 * Home(Pokazuje se sažeta statistika i par savjeta za potrošnju)
 * Entry(Unos podataka, obavijest kad je probijen rekord potrošnje)
 * Stats(Dani, tjedni, mjeseci sort opcija,  količina CO2 emisije)
 * Analiza potrošnje po uređaju(korisnik odabere uređaj i ispiše mu koliko troši u usporedbi s nečim drugim?)
 *
 * LOGIN SCREEN -
 * povezati to sa text datotekom koja sadrži sve user info i pri krivom ulogiranju izbacit error
 *
 */

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}