package com.FileUtilsThreads;

import com.Serialization.ApplianceSerialization;
import com.Serialization.CategorySerialization;
import com.models.Role;
import com.utils.FileUtils;

import java.util.HashSet;
import java.util.Set;

public class FileUtilsThread {
    public static boolean activeConnectionWithFile = false;
    public synchronized Set<Role> dohvatPodatakaORacunima(){
        while(activeConnectionWithFile){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithFile = true;
        notifyAll();
        Set<Role> racuni = FileUtils.dohvatPodatakaORacunima();
        activeConnectionWithFile = false;
        notifyAll();
        return racuni;
    }
    public synchronized void serializeRacune(Set<Role> racuni){
        while(activeConnectionWithFile){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithFile = true;
        notifyAll();
        FileUtils.serializeRacune(racuni);
        activeConnectionWithFile = false;
        notifyAll();
    }
    public synchronized Set<Role> deserializeRacune(){
        Set<Role> racuni = new HashSet<>();
        while(activeConnectionWithFile){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithFile = true;
        notifyAll();
        racuni = FileUtils.deserializeRacune();
        activeConnectionWithFile = false;
        notifyAll();
        return racuni;
    }
    public synchronized void serializeCategories(CategorySerialization categorySerialization){
        while(activeConnectionWithFile){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithFile = true;
        notifyAll();
        FileUtils.serializeCategories(categorySerialization);
        activeConnectionWithFile = false;
        notifyAll();
    }
    public synchronized CategorySerialization deserializeCategories(){
        while(activeConnectionWithFile){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithFile = true;
        notifyAll();
        CategorySerialization categorySerialization = FileUtils.deserializeCategories();
        activeConnectionWithFile = false;
        notifyAll();
        return categorySerialization;
    }
    public synchronized void serializeAppliances(ApplianceSerialization applianceSerialization){
        while(activeConnectionWithFile){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithFile = true;
        notifyAll();
        FileUtils.serializeAppliances(applianceSerialization);
        activeConnectionWithFile = false;
        notifyAll();
    }
    public synchronized ApplianceSerialization deserializeAppliances(){
        while(activeConnectionWithFile){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithFile = true;
        notifyAll();
        ApplianceSerialization applianceSerialized = FileUtils.deserializeAppliances();
        activeConnectionWithFile = false;
        notifyAll();
        return applianceSerialized;
    }
}
