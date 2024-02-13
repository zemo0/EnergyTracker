package com.FileUtilsThreads;

public class DeserializeAppliancesThread extends FileUtilsThread implements Runnable{
    @Override
    public void run(){
        super.deserializeAppliances();
    }
}
