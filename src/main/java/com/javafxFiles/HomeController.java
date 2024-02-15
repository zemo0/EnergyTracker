package com.javafxFiles;

import com.DatabaseThreads.AverageCostByMonthThread;
import com.mainPackage.Main;
import com.models.Appliance;
import com.models.AverageCostPerMonth;
import com.models.Months;
import com.utils.DatabaseUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.mainPackage.Main.fillUpMap;
import static com.mainPackage.Main.fillUpMapOfMaxValues;

public class HomeController implements Initializable {
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private TextArea recommendationsTextArea;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        for(Months month : Months.values()) {
            List<Appliance> appliances = DatabaseUtils.getAppliancesByMonth(String.valueOf(month));
            Double suma = appliances.stream().mapToDouble(Appliance::getTotalCostOfAppliance).sum();
            series1.getData().add(new XYChart.Data<String, Double>(month.toString(), suma));
        }
        barChart.getData().add(series1);

        Timeline refreshThread = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AverageCostByMonthThread thread = new AverageCostByMonthThread();
                Platform.runLater(thread); //javafx ima svoje threadove i ovo se koristi umjesto ovih dolje da ih ne zezne
                //Thread starter = new Thread(thread);
                //starter.start();
            }
        }), new KeyFrame(Duration.seconds(1)));
        refreshThread.setCycleCount(Animation.INDEFINITE);
        refreshThread.play();

        recommendationsTextArea.setText("Preporuke: \n");
        Map<Months, Appliance> mapOfAppliances = new HashMap<>();
        mapOfAppliances = fillUpMap(mapOfAppliances);
        for(Map.Entry<Months, Appliance> entry : mapOfAppliances.entrySet()) {
            AverageCostPerMonth<Months> averageCostPerMonth = new AverageCostPerMonth<>(entry.getKey());
            Double averageCost = averageCostPerMonth.getAverageCostPerMonth();
            if(entry.getValue().getTotalCostOfAppliance() > averageCost) {
                recommendationsTextArea.appendText("U " + entry.getKey() + " potrošnja je veća od prosjeka, možete idući mjesec malo odmorit od " + entry.getValue().getApplianceCategory().getName() + "\n");
            }
        }

        Map<String, Double> map = new HashMap<>(); //najveci potrosac u mjesecu je x i razlika je
        map = fillUpMapOfMaxValues(map);
        Double maxDouble = Double.MIN_VALUE;
        String maxAppliance = null;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getValue() > maxDouble) {
                maxDouble = entry.getValue();
                maxAppliance = entry.getKey();
            }
        }
        if(!DatabaseUtils.getAllAppliances().isEmpty()) {
            recommendationsTextArea.appendText("Vaš najveći potrošač ove godine je " + maxAppliance + " i sveukupno vas je koštao " + maxDouble + "€\n");
        }

    }
}