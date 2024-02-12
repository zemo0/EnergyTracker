package com.Serialization;

import com.models.Category;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategorySerialization implements Serializable {
    private List<String> changeInCategories;
    private List<Category> categoriesBeforeChange;
    private List<Category> categoriesAfterChange;
    private List<LocalDateTime> timeOfChange;
    public CategorySerialization(){
        changeInCategories = new ArrayList<>();
        categoriesBeforeChange = new ArrayList<>();
        categoriesAfterChange = new ArrayList<>();
        timeOfChange = new ArrayList<>();
    }
    public void addChangeInCategories(String change){
        changeInCategories.add(change);
    }
    public void addCategoryBeforeChange(Category category){
        categoriesBeforeChange.add(category);
    }
    public void addCategoryAfterChange(Category category){
        categoriesAfterChange.add(category);
    }
    public void addTimeOfChange(LocalDateTime time){
        timeOfChange.add(time);
    }

    @Override
    public String toString() {
        return "CategorySerialization{" +
                "changeInCategories=" + changeInCategories +
                ", categoriesBeforeChange=" + categoriesBeforeChange +
                ", categoriesAfterChange=" + categoriesAfterChange +
                ", timeOfChange=" + timeOfChange +
                '}';
    }
}
