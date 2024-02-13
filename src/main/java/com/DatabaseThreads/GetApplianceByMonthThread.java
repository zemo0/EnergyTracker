package com.DatabaseThreads;

public class GetApplianceByMonthThread extends DatabaseThread implements Runnable{
    private final String month;
    public GetApplianceByMonthThread(String month){
        this.month = month;
    }
    @Override
    public void run() {
        super.getApplianceByMonth(month);
    }
}
