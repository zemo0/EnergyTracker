package com.Threads;

public class GetAllAppliancesThread extends DatabaseThread implements Runnable{
    @Override
    public void run() {
        super.getAllAppliances();
    }
}
