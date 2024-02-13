package com.DatabaseThreads;

import com.models.Appliance;
import com.models.Category;
import com.utils.DatabaseUtils;

import java.util.List;

public class DatabaseThread {
    public static Boolean activeConnectionWithDatabase = false;
    public synchronized List<Category> getAllCategories(){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        List<Category> list = DatabaseUtils.getAllCategories();//?
        activeConnectionWithDatabase = false;
        notifyAll();
        return list;
    }
    public synchronized List<Appliance> getAllAppliances(){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        List<Appliance> list = DatabaseUtils.getAllAppliances();//?
        activeConnectionWithDatabase = false;
        notifyAll();
        return list;
    }
    public synchronized void insertNewCategory(Category category){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        DatabaseUtils.insertNewCategory(category);
        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void insertNewAppliance(Appliance appliance){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        DatabaseUtils.insertNewAppliance(appliance);
        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void updateCategory(Category category, Long id){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        DatabaseUtils.updateCategory(category, id);
        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void updateAppliance(Appliance appliance, Long id){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        DatabaseUtils.updateAppliance(appliance, id);
        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void deleteCategory(Category category){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        DatabaseUtils.deleteCategory(category);
        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void deleteAppliance(Appliance appliance){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        DatabaseUtils.deleteAppliance(appliance);
        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized List<Appliance> getApplianceByMonth(String month){
        while(activeConnectionWithDatabase){
            try{
                wait();
            } catch(InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
        activeConnectionWithDatabase = true;
        notifyAll();
        List<Appliance> list = DatabaseUtils.getAppliancesByMonth(month);
        activeConnectionWithDatabase = false;
        notifyAll();
        return list;
    }
}
