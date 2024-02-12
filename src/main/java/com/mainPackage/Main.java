package com.mainPackage;

import com.Threads.GetAllAppliancesThread;
import com.Threads.GetAllCategoriesThread;
import com.models.Appliance;
import com.models.Category;
import com.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Objects;

public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static Long getCategoryId(Category category){
        GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
        List<Category> categories = getAllCategoriesThread.getAllCategories();
        Long id = null;
        for(Category c : categories){
            if(c.getName().equals(category.getName())){
                id = c.getId();
            }
        }
        return id;
    }
    public static Category getCategoryBasedOnName(String name){
        GetAllCategoriesThread getAllCategoriesThread = new GetAllCategoriesThread();
        List<Category> categories = getAllCategoriesThread.getAllCategories();
        long id = 0;
        Category category = null;
        for(Category c : categories){
            if(c.getName().equals(name)){
                category = c;
            }
        }
        return category;
    }
    public static void addDuplicateAppliances(Appliance appliance){
        GetAllAppliancesThread getAllAppliancesThread = new GetAllAppliancesThread();
        List<Appliance> appliances = getAllAppliancesThread.getAllAppliances();
        for(Appliance a : appliances){
            if(a.getApplianceCategory().getName().equals(appliance.getApplianceCategory().getName()) &&
                    a.getTariff() == appliance.getTariff() &&
                    a.getMonth().equals(appliance.getMonth()) &&
                    Objects.equals(a.getAppliancePowerUse(), appliance.getAppliancePowerUse())){
                a.setDailyConsumption(a.getDailyConsumption() + appliance.getDailyConsumption());
                a.setDailyUseTime(a.getDailyUseTime() + appliance.getDailyUseTime());
                a.setTotalCostOfAppliance(a.getTotalCostOfAppliance() + appliance.getTotalCostOfAppliance());
                DatabaseUtils.updateAppliance(a, a.getId());
                return;
            }
        }
    }
    public static boolean checkForDuplicateAppliances(Appliance appliance){
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        for(Appliance a : appliances){
            if(a.getApplianceCategory().getName().equals(appliance.getApplianceCategory().getName()) &&
                    a.getTariff() == appliance.getTariff() &&
                    a.getMonth().equals(appliance.getMonth()) &&
                    Objects.equals(a.getAppliancePowerUse(), appliance.getAppliancePowerUse())){
                return true;
            }
        }
        return false;
    }
}
