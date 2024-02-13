package com.DatabaseThreads;

public class GetAllAppliancesThread extends DatabaseThread implements Runnable{
    @Override
    public void run() {
        super.getAllAppliances();
    }
}
