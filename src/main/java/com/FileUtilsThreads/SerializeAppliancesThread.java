package com.FileUtilsThreads;

import com.Serialization.ApplianceSerialization;

public class SerializeAppliancesThread extends FileUtilsThread implements Runnable{
    private ApplianceSerialization applianceSerialization;
    public SerializeAppliancesThread(ApplianceSerialization applianceSerialization){
        this.applianceSerialization = applianceSerialization;
    }
    @Override
    public void run() {
        super.serializeAppliances(applianceSerialization);
    }
}
