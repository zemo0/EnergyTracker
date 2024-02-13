package com.FileUtilsThreads;
import com.models.Role;

import java.util.Set;

public class SerializeRacuneThread extends FileUtilsThread implements Runnable{
    private Set<Role> racuni;
    public SerializeRacuneThread(Set<Role> racuni){
        this.racuni = racuni;
    }
    @Override
    public void run() {
        super.serializeRacune(racuni);
    }
}
