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
 * iskoristiti mapu negdje, genericnu listu s jednim parametrom
 * dodati tablicu u kojoj pisu svi useri za admina, DOVRŠI EDIT I DELETE KORISNIKA
 * preporuke za uštedu na home screenu ispisati ispod grafa
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