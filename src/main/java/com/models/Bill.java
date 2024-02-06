package com.models;

import java.util.HashMap;
import java.util.Map;

public class Bill implements DailyConsumption{
    private Double dailyCost = 0.0;
    private static Map<Months, Appliance> troskovi = new HashMap<>();

    public static void dodajTrošak(Months month, Appliance appliance){
        troskovi.put(month, appliance);
    }

    @Override
    public void dailyConsumption(Double appliancePowerUse, Double dailyUseTime) {
        dailyCost = (appliancePowerUse/1000) * dailyUseTime;
    }
    public Double getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(Double dailyCost) {
        this.dailyCost = dailyCost;
    }
}
