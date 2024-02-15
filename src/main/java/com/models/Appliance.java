package com.models;

import com.DatabaseThreads.GetAllCategoriesThread;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Appliance implements Serializable {
    private Long id;
    private Category applianceCategory;
    private String username;
    private String month;
    private Double appliancePowerUse;
    private Double dailyUseTime;
    private Boolean tariff; //true = dnevna, false = nocna
    private Double dailyConsumption; // appPowerUse * dailyUseTime
    private Double totalCostOfAppliance; // dailyConsumption * tariff
    public Appliance(ApplianceBuilder builder){
        this.id = builder.id;
        this.applianceCategory = builder.applianceCategory;
        this.username = builder.username;
        this.month = builder.month;
        this.appliancePowerUse = builder.appliancePowerUse;
        this.dailyUseTime = builder.dailyUseTime;
        this.tariff = builder.tariff;
        this.dailyConsumption = builder.dailyConsumption;
        this.totalCostOfAppliance = builder.totalCostOfAppliance;
    }
    public static class ApplianceBuilder{
        private Long id;
        private Category applianceCategory;
        private String username;
        private String month;
        private Double appliancePowerUse;
        private Double dailyUseTime;
        private Boolean tariff;
        private Double dailyConsumption;
        private Double totalCostOfAppliance;
        public ApplianceBuilder id(Long id){
            this.id = id;
            return this;
        }
        public ApplianceBuilder categoryId(Long categoryId){
            GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
            List<Category> categories = getAllCategoriesThread.getAllCategories();
            for(Category category : categories){
                if(category.getId().equals(categoryId)){
                    this.applianceCategory = category;
                }
            }
            return this;
        }
        public ApplianceBuilder category(Category category){
            this.applianceCategory = category;
            return this;
        }
        public ApplianceBuilder username(String username){
            this.username = username;
            return this;
        }
        public ApplianceBuilder month(Months month){
            this.month = month.toString();
            return this;
        }
        public ApplianceBuilder appliancePowerUse(Double appliancePowerUse){
            this.appliancePowerUse = appliancePowerUse;
            return this;
        }
        public ApplianceBuilder dailyUseTime(Double dailyUseTime){
            this.dailyUseTime = dailyUseTime;
            return this;
        }
        public ApplianceBuilder tariff(Boolean tariff){
            this.tariff = tariff;
            return this;
        }
        public ApplianceBuilder dailyConsumption(Double dailyConsumption){
            this.dailyConsumption = dailyConsumption;
            return this;
        }
        public ApplianceBuilder totalCostOfAppliance(Double totalCostOfAppliance){
            this.totalCostOfAppliance = totalCostOfAppliance;
            return this;
        }
        public Appliance build(){return new Appliance(this);}
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getApplianceCategory() {
        return applianceCategory;
    }

    public void setApplianceCategory(Category applianceCategory) {
        this.applianceCategory = applianceCategory;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getTotalCostOfAppliance() {
        return totalCostOfAppliance;
    }

    public void setTotalCostOfAppliance(Double totalCostOfAppliance) {
        this.totalCostOfAppliance = totalCostOfAppliance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appliance appliance = (Appliance) o;
        return Objects.equals(id, appliance.id) && Objects.equals(applianceCategory, appliance.applianceCategory) && Objects.equals(username, appliance.username) && Objects.equals(month, appliance.month) && Objects.equals(appliancePowerUse, appliance.appliancePowerUse) && Objects.equals(dailyUseTime, appliance.dailyUseTime) && Objects.equals(tariff, appliance.tariff) && Objects.equals(dailyConsumption, appliance.dailyConsumption) && Objects.equals(totalCostOfAppliance, appliance.totalCostOfAppliance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applianceCategory, username, month, appliancePowerUse, dailyUseTime, tariff, dailyConsumption, totalCostOfAppliance);
    }

    @Override
    public String toString() {
        return "ApplianceCategory=" + applianceCategory +
                ", month='" + month + '\'' +
                ", appliancePowerUse=" + appliancePowerUse +
                ", dailyUseTime=" + dailyUseTime +
                ", tariff=" + tariff +
                ", dailyConsumption=" + dailyConsumption;
    }
}
