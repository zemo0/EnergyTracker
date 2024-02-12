package com.Serialization;
import com.models.Appliance;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class ApplianceSerialization implements Serializable {
    private List<String> changeInAppliances;
    private List<Appliance> appliancesBeforeChange;
    private List<Appliance> appliancesAfterChange;
    private List<LocalDateTime> timeOfChange;
    public ApplianceSerialization(){
        changeInAppliances = new ArrayList<>();
        appliancesBeforeChange = new ArrayList<>();
        appliancesAfterChange = new ArrayList<>();
        timeOfChange = new ArrayList<>();
    }
    public void addChangeInAppliances(String change){
        changeInAppliances.add(change);
    }
    public void addApplianceBeforeChange(Appliance appliance){
        appliancesBeforeChange.add(appliance);
    }
    public void addApplianceAfterChange(Appliance appliance){
        appliancesAfterChange.add(appliance);
    }
    public void addTimeOfChange(LocalDateTime time){
        timeOfChange.add(time);
    }

    public List<String> getChangeInAppliances() {
        return changeInAppliances;
    }

    public void setChangeInAppliances(List<String> changeInAppliances) {
        this.changeInAppliances = changeInAppliances;
    }

    public List<Appliance> getAppliancesBeforeChange() {
        return appliancesBeforeChange;
    }

    public void setAppliancesBeforeChange(List<Appliance> appliancesBeforeChange) {
        this.appliancesBeforeChange = appliancesBeforeChange;
    }

    public List<Appliance> getAppliancesAfterChange() {
        return appliancesAfterChange;
    }

    public void setAppliancesAfterChange(List<Appliance> appliancesAfterChange) {
        this.appliancesAfterChange = appliancesAfterChange;
    }

    public List<LocalDateTime> getTimeOfChange() {
        return timeOfChange;
    }

    public void setTimeOfChange(List<LocalDateTime> timeOfChange) {
        this.timeOfChange = timeOfChange;
    }
}
