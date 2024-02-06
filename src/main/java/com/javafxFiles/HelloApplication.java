package com.javafxFiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.mainPackage.Main.logger;

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
 * Analiza potrošnje po uređaju(korisnik odabere uređaj i ispiše mu koliko troši u usporedbi s nečim drugim?)
 *
 * KLASE:
 * Mjesec potrošnje(ENUMERACIJA)
 * Kategorija trošila(dodaj novu, izmjeni, izbrisi)
 * Potrošnja proizvoda(ostala dve klase ukomponirane u ovu -> dodaj, izmjeni, izbrisi)
 * Račun(ukupni trošak i ispis svih troškova za taj mjesec, samo lijepi prikaz), prikazat ga na home pageu
 *
 * svaka klasa mora bit svoj meni
 * nemoj komplicirat zasad, napravi sve kako zahtijeva i onda dodavaj nove stvari
 *
 * Bill ima mapu za svaki mjesec, u mapu se dodaju troškovi i dailycost se treba dodati u Appliance klasu kao varijabla
 * da bi ju mogao ispisati u billu za svaki appliance batonga
 * Napravit funkciju koja zbraja dupliakte i ubaci ih u bazu
 * Saznati što napraviti kao generičnu klasu
 *
 */

public class HelloApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Program je pokrenut...");
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static Stage getStage() {
        return primaryStage;
    }
}