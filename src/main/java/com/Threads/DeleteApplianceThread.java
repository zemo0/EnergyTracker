package com.Threads;

import com.models.Appliance;

public class DeleteApplianceThread extends DatabaseThread implements Runnable{
    private Appliance appliance;
    public DeleteApplianceThread(Appliance appliance){
        this.appliance = appliance;
    }
    public void run() {
        super.deleteAppliance(appliance);
    }
}
