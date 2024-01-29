package com.javafxFiles;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuBarController {

    public void showEnterNewApplianceScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "enterNewApplianceScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 850, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getStage().setScene(scene);
        HelloApplication.getStage().show();
    }
}
