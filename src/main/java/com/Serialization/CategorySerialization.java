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
    private List<String> roleThatChanged;
    private List<LocalDateTime> timeOfChange;
    public CategorySerialization(){
        changeInCategories = new ArrayList<>();
        categoriesBeforeChange = new ArrayList<>();
        categoriesAfterChange = new ArrayList<>();
        roleThatChanged = new ArrayList<>();
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
    public void addRoleThatChanged(String role){
        roleThatChanged.add(role);
    }

    public List<String> getRoleThatChanged() {
        return roleThatChanged;
    }

    public void setRoleThatChanged(List<String> roleThatChanged) {
        this.roleThatChanged = roleThatChanged;
    }

    public List<String> getChangeInCategories() {
        return changeInCategories;
    }

    public void setChangeInCategories(List<String> changeInCategories) {
        this.changeInCategories = changeInCategories;
    }

    public List<Category> getCategoriesBeforeChange() {
        return categoriesBeforeChange;
    }

    public void setCategoriesBeforeChange(List<Category> categoriesBeforeChange) {
        this.categoriesBeforeChange = categoriesBeforeChange;
    }

    public List<Category> getCategoriesAfterChange() {
        return categoriesAfterChange;
    }

    public void setCategoriesAfterChange(List<Category> categoriesAfterChange) {
        this.categoriesAfterChange = categoriesAfterChange;
    }

    public List<LocalDateTime> getTimeOfChange() {
        return timeOfChange;
    }

    public void setTimeOfChange(List<LocalDateTime> timeOfChange) {
        this.timeOfChange = timeOfChange;
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
