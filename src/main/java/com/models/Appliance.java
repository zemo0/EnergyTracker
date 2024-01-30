package com.models;

import javafx.fxml.FXML;

public class Appliance {
    private Double appliancePowerUse;
    private Double dailyUseTime;
    private Boolean tariff; //true = dnevna, false = nocna
    private Double dailyConsumption; // appPowerUse * dailyUseTime
    public Appliance(ApplianceBuilder builder){
        this.appliancePowerUse = builder.appliancePowerUse;
        this.dailyUseTime = builder.dailyUseTime;
        this.tariff = builder.tariff;
        this.dailyConsumption = builder.dailyConsumption;
    }
    public static class ApplianceBuilder{
        private Double appliancePowerUse;
        private Double dailyUseTime;
        private Boolean tariff;
        private Double dailyConsumption;
        public ApplianceBuilder appliancePowerUse(Double appliancePowerUse){
            this.appliancePowerUse = appliancePowerUse;
            return this;
        }
        public ApplianceBuilder dailyUseTime(Double dailyUseTime){
            this.dailyUseTime = dailyUseTime;
            return this;
        }
        public ApplianceBuilder tarrif(Boolean tariff){
            this.tariff = tariff;
            return this;
        }
        public ApplianceBuilder dailyConsumption(Double dailyConsumption){
            this.dailyConsumption = dailyConsumption;
            return this;
        }
        public Appliance build(){return new Appliance(this);}
    }

    public Double getAppliancePowerUse() {
        return appliancePowerUse;
    }

    public void setAppliancePowerUse(Double appliancePowerUse) {
        this.appliancePowerUse = appliancePowerUse;
    }

    public Double getDailyUseTime() {
        return dailyUseTime;
    }

    public void setDailyUseTime(Double dailyUseTime) {
        this.dailyUseTime = dailyUseTime;
    }

    public Boolean getTariff() {
        return tariff;
    }

    public void setTariff(Boolean tariff) {
        this.tariff = tariff;
    }

    public Double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(Double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }
}