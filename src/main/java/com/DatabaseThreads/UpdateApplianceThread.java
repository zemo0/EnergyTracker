package com.DatabaseThreads;
import com.models.Appliance;

public class UpdateApplianceThread extends DatabaseThread implements Runnable{
    private final Appliance appliance;
    private final Long id;
    public UpdateApplianceThread(Appliance appliance, Long id){
        this.appliance = appliance;
        this.id = id;
    }
    @Override
    public void run() { updateAppliance(appliance, id);}
}
