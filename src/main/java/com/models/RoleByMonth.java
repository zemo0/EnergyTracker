package com.models;

import com.utils.DatabaseUtils;

import java.util.List;

public class RoleByMonth<T extends Role, U extends List<Months>>{
    private T role;
    private U months;

    public RoleByMonth(T role, U months) {
        this.role = role;
        this.months = months;
    }
    public Double getAverageBill(){
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        double sum = 0;
        int counter = 0;
        for(Months month : months){
            sum += appliances.stream().filter(appliance -> appliance.getMonth().equals(month.toString())).mapToDouble(Appliance::getTotalCostOfAppliance).sum();
            if(appliances.stream().anyMatch(appliance -> appliance.getMonth().equals(month.toString()))) {
                counter++;
            }
        }
        return sum/counter;
    }
    public T getRole() {
        return role;
    }

    public void setRole(T role) {
        this.role = role;
    }

    public U getMonths() {
        return months;
    }

    public void setMonths(U months) {
        this.months = months;
    }
}
