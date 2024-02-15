package com.mainPackage;

import com.DatabaseThreads.GetAllAppliancesThread;
import com.DatabaseThreads.GetAllCategoriesThread;
import com.models.Appliance;
import com.models.Category;
import com.models.Months;
import com.utils.DatabaseUtils;
import javafx.scene.chart.XYChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
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
    public static boolean isCategoryUsedInAppliances(Category category){
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        for(Appliance a : appliances){
            if(a.getApplianceCategory().getName().equals(category.getName())){
                return true;
            }
        }
        return false;
    }

    public static Map<Months, Appliance> fillUpMap(Map<Months, Appliance> mapOfAppliances){
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        for(Months month : Months.values()){
            for(Appliance appliance : appliances){
                if(appliance.getMonth().equals(month.toString())){
                    mapOfAppliances.put(month, appliance);
                }
            }
        }
        return mapOfAppliances;
    }
    public static Map<String, Double> fillUpMapOfMaxValues(Map<String, Double> mapOfAppliances){
        List<Appliance> appliances = DatabaseUtils.getAllAppliances();
        Map<String, Double> mapOfDoubles = new HashMap<>();
        for(Appliance appliance : appliances){
            Double currentValue = mapOfDoubles.get(appliance.getApplianceCategory().getName());
            if (currentValue == null) {
                mapOfDoubles.put(appliance.getApplianceCategory().getName(), appliance.getTotalCostOfAppliance());
            } else {
                mapOfDoubles.put(appliance.getApplianceCategory().getName(), currentValue + appliance.getTotalCostOfAppliance());
            }
        }
        return mapOfDoubles;
    }
}
