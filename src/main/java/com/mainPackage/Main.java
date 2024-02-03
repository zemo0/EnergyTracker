package com.mainPackage;

import com.models.Category;
import com.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static Long getCategoryId(Category category){
        List<Category> categories = DatabaseUtils.getAllCategories();
        Long id = null;
        for(Category c : categories){
            if(c.getName().equals(category.getName())){
                id = c.getId();
            }
        }
        return id;
    }
    public static Category getCategoryBasedOnName(String name){
        List<Category> categories = DatabaseUtils.getAllCategories();
        long id = 0;
        Category category = null;
        for(Category c : categories){
            if(c.getName().equals(name)){
                category = c;
            }
        }
        return category;
    }
}
