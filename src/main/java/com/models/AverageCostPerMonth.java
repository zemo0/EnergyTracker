package com.models;

import com.utils.DatabaseUtils;

import java.util.List;

public class AverageCostPerMonth<T extends Months> {
    private T month;
    public AverageCostPerMonth(T month) {
        this.month = month;
    }
    public Double getAverageCostPerMonth() {
        List<Appliance> appliances = DatabaseUtils.getAppliancesByMonth(String.valueOf(month));
        Double averageCost = appliances.stream().mapToDouble(Appliance::getTotalCostOfAppliance).sum() / appliances.size();
        return averageCost;
    }
}
