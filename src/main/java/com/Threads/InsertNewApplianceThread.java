package com.Threads;

import com.models.Appliance;

public class InsertNewApplianceThread extends DatabaseThread implements Runnable{
    private final Appliance appliance;
    public InsertNewApplianceThread(Appliance appliance){
        this.appliance = appliance;
    }
    @Override
    public void run() {
        super.insertNewAppliance(appliance);
    }
}
