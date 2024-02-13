package com.DatabaseThreads;
import com.models.Category;
public class InsertNewCategoryThread extends DatabaseThread implements Runnable{
    private final Category category;
    public InsertNewCategoryThread(Category category){
        this.category = category;
    }
    @Override
    public void run() {
        super.insertNewCategory(category);
    }
}
